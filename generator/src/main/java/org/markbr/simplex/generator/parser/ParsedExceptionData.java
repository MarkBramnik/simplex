package org.markbr.simplex.generator.parser;

import lombok.Builder;
import lombok.Value;
import org.markbr.simplex.generator.sourcecode.DataField;

import java.util.List;

@Value
@Builder
public class ParsedExceptionData {
    private final String packageName;
    private final String exceptionClassName;
    private final String baseExceptionPackageName;
    private final String baseExceptionName;
    private final List<DataField> params;
    private final int httpStatus;
    private final int code;
    private final String rawMessage;
}
