package org.markbr.simplex.generator.sourcecode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SourceCodeBaseExceptionClassGeneratorImplTest {
    @Test
    @DisplayName("base exception source code is generated successfully")
    public void test_base_exception_generated() {
        SourceCodeBaseExceptionClassGeneratorImpl underTest = new SourceCodeBaseExceptionClassGeneratorImpl();
        String actual = underTest.generateBaseClassSourceCode("com.sample.project", "BaseProjectException");
        System.out.println(actual);
    }

}