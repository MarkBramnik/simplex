package org.markbr.simplex.generator.sourcecode;

import org.markbr.simplex.generator.parser.ParsedExceptionData;

public interface SourceCodeGenerator {

    String generateSourceCode(ParsedExceptionData exceptionData);
}
