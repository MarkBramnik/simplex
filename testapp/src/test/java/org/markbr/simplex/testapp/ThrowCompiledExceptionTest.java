package org.markbr.simplex.testapp;

import org.junit.jupiter.api.Test;
import org.markbr.simplex.testapp.exceptions.TooLargeIdException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThrowCompiledExceptionTest {

    @Test
    public void testThrownTooLargeIdException() {
        TooLargeIdException ex = new TooLargeIdException(100, 200);
        assertEquals(1000, ex.getErrorCode());
        assertEquals(400, ex.getHttpStatus());
        assertEquals(100, ex.getValue().intValue());
        assertEquals(200, ex.getMaxValue().intValue());
        assertEquals("The Field has value {{value:Integer}} which is more than maximum allowed value of {{maxValue:Integer}}", ex.getRawMessage());
        assertEquals("The Field has value 100 which is more than maximum allowed value of 200", ex.getMessage());
    }
}
