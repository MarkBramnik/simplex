package org.markbr.simplex.generator.sourcecode;

import com.squareup.javapoet.*;
import org.markbr.simplex.generator.parser.ParameterResolverUtil;
import org.markbr.simplex.generator.parser.ParsedExceptionData;

import javax.lang.model.element.Modifier;
import java.util.*;

import static org.markbr.simplex.generator.sourcecode.SourceCodeGenerationUtils.toGetterName;

public class SourceCodeRealExceptionGeneratorImpl implements SourceCodeGenerator {
    @Override
    public String generateSourceCode(ParsedExceptionData exceptionData) {
        FieldSpec errorCodeFieldSpec     = FieldSpec.builder(int.class, "errorCode",  Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL).initializer("$L", exceptionData.getCode()).build();
        FieldSpec httpStatusFieldSpec    = FieldSpec.builder(int.class, "httpStatus", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL).initializer("$L", exceptionData.getHttpStatus()).build();
        FieldSpec rawMessageFieldSpec    = FieldSpec.builder(String.class, "rawMessage", Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL).initializer("$S", exceptionData.getRawMessage()).build();
        FieldSpec parametersMapFieldSpec = FieldSpec.builder(ParameterizedTypeName.get(Map.class, String.class, Object.class), "parameters", Modifier.PRIVATE, Modifier.FINAL).build();
        MethodSpec getterErrorCodeSpec   = generateGetterFromStaticField("errorCode", int.class);
        MethodSpec getterHttpStatusSpec  = generateGetterFromStaticField("httpStatus", int.class);
        MethodSpec getterRawMessageSpec  = generateGetterFromStaticField("rawMessage", String.class);
        MethodSpec getterParametersSpec  = generateGetterForParameters(exceptionData.getParams());



        List<FieldSpec> parameterNamesFieldSpecList = prepareParameterSpecList(exceptionData.getParams());

        MethodSpec constructorNoThrowable   = generateConstructor(exceptionData, false);
        MethodSpec constructorWithThrowable = generateConstructor(exceptionData, true);


        TypeSpec.Builder
                classBuilder = TypeSpec.classBuilder(ClassName.get(exceptionData.getPackageName(), exceptionData.getExceptionClassName())).addModifiers(Modifier.PUBLIC)
                .superclass(ClassName.get(exceptionData.getBaseExceptionPackageName(), exceptionData.getBaseExceptionName()));

        for(FieldSpec paramNameFieldSpec : parameterNamesFieldSpecList) {
            classBuilder.addField(paramNameFieldSpec);
        }

        classBuilder.addField(errorCodeFieldSpec)
                    .addField(httpStatusFieldSpec)
                    .addField(rawMessageFieldSpec);


        if(hasParameters(exceptionData.getParams())) {
            classBuilder.addField(parametersMapFieldSpec);
        }
        classBuilder.addMethod(constructorNoThrowable);
        classBuilder.addMethod(constructorWithThrowable);

        for(DataField param : exceptionData.getParams()) {
            MethodSpec getter = generateGetter(param.getFieldName(), param.getDataType().getFieldType());
            classBuilder.addMethod(getter);
        }

        classBuilder.addMethod(getterErrorCodeSpec);
        classBuilder.addMethod(getterHttpStatusSpec);
        classBuilder.addMethod(getterRawMessageSpec);
        classBuilder.addMethod(getterParametersSpec);


        JavaFile javaFile = JavaFile.builder(exceptionData.getPackageName(), classBuilder.build()).build();
        return javaFile.toString();
    }

    private List<FieldSpec> prepareParameterSpecList(List<DataField> params) {
        if(!hasParameters(params)) {
            return Collections.emptyList();
        }
        List<FieldSpec> result = new ArrayList<>(params.size());
        for(DataField dataField : params) {
             result.add(
               FieldSpec.builder(String.class, toParamName(dataField.getFieldName()), Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL).initializer("$S", dataField.getFieldName()).build()
             );
        }
        return result;
    }

    private boolean hasParameters(List<DataField> params) {
        return !params.isEmpty();
    }

    private MethodSpec generateConstructor(ParsedExceptionData exceptionData, boolean withThrowableLastParam) {
        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC);

        for(DataField dataField : exceptionData.getParams()) {
            constructorBuilder.addParameter(dataField.getDataType().getFieldType(), dataField.getFieldName());
        }
        if(withThrowableLastParam) {
            constructorBuilder.addParameter(Throwable.class, "cause");
            constructorBuilder.addStatement("super(" + generateInterpolatedString(exceptionData.getRawMessage()) + ", cause)");
        } else {
            constructorBuilder.addStatement("super(" + generateInterpolatedString(exceptionData.getRawMessage()) + ")");
        }
        if(hasParameters(exceptionData.getParams())) {
            constructorBuilder.addStatement("parameters = new $T<>()", HashMap.class);
        }
        for(DataField dataField : exceptionData.getParams()) {
            constructorBuilder.addStatement("parameters.put( " + toParamName(dataField.getFieldName()) + " , " + dataField.getFieldName()+ " )");
        }


        return constructorBuilder.build();
    }

    private MethodSpec generateGetter(String paramName, Class<?> returnType) {
        return MethodSpec.methodBuilder(toGetterName(paramName))
                .returns(returnType)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return ($T)parameters.get( " + toParamName(paramName) + " )", returnType )
                .build();
    }

    private MethodSpec generateGetterFromStaticField(String paramName, Class<?> returnType) {
        return MethodSpec.methodBuilder(toGetterName(paramName))
                .returns(returnType)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return " + paramName)
                .build();
    }

    private MethodSpec generateGetterForParameters(List<DataField> params) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder(toGetterName("parameters"))
                .returns(ParameterizedTypeName.get(Map.class, String.class, Object.class))
                .addModifiers(Modifier.PUBLIC);
        if(hasParameters(params)) {
            builder.addStatement("return parameters");
        }
        else {
            builder.addStatement( "return $T.emptyMap()", Collections.class);
        }
        return builder.build();


    }

    private String toParamName(String fieldName) {
        return fieldName.concat("_PARAM_NAME");
    }


    private String generateInterpolatedString(String rawMessage) {
        return ParameterResolverUtil.transformToInterpolatedString(rawMessage);
    }
}
