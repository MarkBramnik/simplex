package org.markbr.simplex.generator.sourcecode;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.util.Map;

import static org.markbr.simplex.generator.sourcecode.SourceCodeGenerationUtils.toGetterName;

public class SourceCodeBaseExceptionClassGeneratorImpl implements SourceCodeBaseClassGenerator {

    @Override
    public String generateBaseClassSourceCode(String basePackageName, String baseClassName) {

        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(
                ClassName.get(
                        basePackageName,
                        baseClassName
                ))
                .superclass(ClassName.get(RuntimeException.class))
                .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

        MethodSpec constructorMsg = generateConstructor(false);
        MethodSpec constructorMsgAndThrowable = generateConstructor(true);

        MethodSpec errorCodeGetter  = generateAbstractGetter("errorCode",  TypeName.get(int.class));
        MethodSpec httpStatusGetter = generateAbstractGetter("httpStatus", TypeName.get(int.class));
        MethodSpec rawMessageGetter = generateAbstractGetter("rawMessage", TypeName.get(String.class));
        MethodSpec parametersGetter = generateAbstractGetter("parameters", ParameterizedTypeName.get(Map.class, String.class, Object.class));

        classBuilder
                .addMethod(constructorMsg)
                .addMethod(constructorMsgAndThrowable)
                .addMethod(errorCodeGetter)
                .addMethod(httpStatusGetter)
                .addMethod(rawMessageGetter)
                .addMethod(parametersGetter);
        JavaFile javaFile = JavaFile.builder(basePackageName, classBuilder.build()).build();
        return javaFile.toString();
    }

    private MethodSpec generateConstructor(boolean withThrowable) {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "message");
        if(withThrowable) {
            builder.addParameter(Throwable.class, "cause");
            builder.addStatement("super(message,cause)");
        }
        else {
            builder.addStatement("super(message)");
        }
        return builder.build();
    }

    private MethodSpec generateAbstractGetter(String paramName, TypeName returnType) {
        return MethodSpec.methodBuilder(toGetterName(paramName))
                .returns(returnType)
                .addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
                .build();
    }


}
