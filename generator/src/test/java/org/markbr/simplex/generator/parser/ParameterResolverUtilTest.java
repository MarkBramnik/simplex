package org.markbr.simplex.generator.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.markbr.simplex.generator.sourcecode.DataField;

import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.markbr.simplex.generator.sourcecode.DataType.*;

class ParameterResolverUtilTest {

    @Test
    @DisplayName("Parameters resolved correctly upon sample correct input")
    public void testParamsResolvedCorrectly() {

        assertAll(
                () -> assertEquals(Collections.emptyList(),
                        ParameterResolverUtil.resolveParams("Hello World")),
                () -> assertEquals(asList(new DataField("value", INT_PRIMITIVE), new DataField("maxValue", INTEGER)),
                                        ParameterResolverUtil.resolveParams("{{value:int}} is more than {{ maxValue: Integer }}, ok?")),
                () -> assertEquals(singletonList(new DataField("name", STRING)),
                        ParameterResolverUtil.resolveParams("{{name:string}} must start with an uppercase"))
        );
    }

    @Test
    @DisplayName("Parameters interpolation resolves for various inputs")
    public void testParametersInterpolation() {
        assertAll(
                () -> assertEquals("\"\"", ParameterResolverUtil.transformToInterpolatedString("")),
                () -> assertEquals("\"Hello World\"", ParameterResolverUtil.transformToInterpolatedString("Hello World")),
                () -> assertEquals("\"Hello \"+ name +\". How are you?\"", ParameterResolverUtil.transformToInterpolatedString( "Hello {{name:String}}. How are you?" )),
                () -> assertEquals("\"Hello, \"+ name", ParameterResolverUtil.transformToInterpolatedString("Hello, {{name:String}}")),
                () -> assertEquals("\"name +\", Hello\"", ParameterResolverUtil.transformToInterpolatedString("{{name:String}}, Hello")),
                () -> assertEquals("\"value +\" exceeds \"+ maxValue +\".\"", ParameterResolverUtil.transformToInterpolatedString( "{{value:Integer}} exceeds {{maxValue:Integer}}." )),
                () -> assertEquals("\"value +\" exceeds \"+ maxValue",  ParameterResolverUtil.transformToInterpolatedString( "{{value:Integer}} exceeds {{maxValue:Integer}}" ))
        );
    }

}