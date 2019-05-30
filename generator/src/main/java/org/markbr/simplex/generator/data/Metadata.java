package org.markbr.simplex.generator.data;

import lombok.Value;

@Value
public class Metadata {
    private final String exceptionsPackageName;
    private final String baseExceptionPackageName;
    private final String baseExceptionClassName;
    private final boolean generateBaseClass;
}
