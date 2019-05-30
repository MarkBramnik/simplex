package org.markbr.simplex.generator.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpperCaseToCamelCaseUtilTest {

    @Test
    @DisplayName("translation is done correctly")
    void translate() {
        assertAll(
                () -> assertEquals("IdIsIllegal", UpperCaseToCamelCaseUtil.translate("ID_IS_ILLEGAL")),
                () -> assertEquals("", UpperCaseToCamelCaseUtil.translate("")),
                () -> assertEquals("RuntimeException123", UpperCaseToCamelCaseUtil.translate("RUNTIME_EXCEPTION_123"))
        );
    }
}