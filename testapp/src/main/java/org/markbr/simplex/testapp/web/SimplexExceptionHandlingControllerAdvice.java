package org.markbr.simplex.testapp.web;

import org.markbr.simplex.testapp.exceptions.BaseTestappException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SimplexExceptionHandlingControllerAdvice {

    @ExceptionHandler(BaseTestappException.class)
    public ResponseEntity<ErrorInfo> handleBaseTestappException(BaseTestappException ex) {
        ErrorInfo result =
            ErrorInfo.builder()
                    .errorCode(ex.getErrorCode())
                    .parameters(ex.getParameters())
                    .rawMessage(ex.getRawMessage())
                    .message(ex.getMessage())
                    .build();
        return ResponseEntity.status(ex.getHttpStatus()).body(result);
    }
}
