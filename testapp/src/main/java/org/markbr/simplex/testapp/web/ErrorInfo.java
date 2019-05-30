package org.markbr.simplex.testapp.web;

import lombok.Builder;
import lombok.Value;

import java.util.Map;


@Builder
@Value
public class ErrorInfo {
    private int errorCode;
    private Map<String, Object> parameters;
    private String rawMessage;
    private String message;
}
