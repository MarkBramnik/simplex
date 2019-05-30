package testutils;

import org.markbr.simplex.generator.parser.ParsedExceptionData;
import org.markbr.simplex.generator.sourcecode.DataField;
import org.markbr.simplex.generator.sourcecode.DataType;

import java.util.Arrays;

public class TestDataFactory {
    public static ParsedExceptionData prepareTestParsedExceptionData () {

        ParsedExceptionData data = ParsedExceptionData.builder()
                .packageName("com.sample.exceptions")
                .baseExceptionPackageName("com.sample.project")
                .baseExceptionName("BaseProjectException")
                .exceptionClassName("TooLargeIdException")
                .code(1000)
                .httpStatus(400)
                .rawMessage("The Field has value {{value:Integer}} which is more than maximum allowed value of {{maxValue:Integer}}")
                .params(Arrays.asList(new DataField("value", DataType.INTEGER), new DataField("maxValue", DataType.INTEGER)))
                .build();
        return data;
    }
}
