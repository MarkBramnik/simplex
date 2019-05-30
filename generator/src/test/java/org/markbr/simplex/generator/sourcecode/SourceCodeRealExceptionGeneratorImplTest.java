package org.markbr.simplex.generator.sourcecode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.markbr.simplex.generator.parser.ParsedExceptionData;
import testutils.TestDataFactory;

class SourceCodeRealExceptionGeneratorImplTest {

    @Test
    @DisplayName("sample exception source code is generated successfully")
    public void test_sample_exception_generated() {
        ParsedExceptionData inputData = TestDataFactory.prepareTestParsedExceptionData();
        SourceCodeRealExceptionGeneratorImpl underTest = new SourceCodeRealExceptionGeneratorImpl();
        String actual = underTest.generateSourceCode(inputData);
        System.out.println(actual);
    }

}