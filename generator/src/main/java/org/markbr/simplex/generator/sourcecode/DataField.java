package org.markbr.simplex.generator.sourcecode;

import lombok.Value;

@Value
public class DataField {
    private final String fieldName;
    private final DataType dataType;
}
