package org.markbr.simplex.generator.sourcecode;

class SourceCodeGenerationUtils {
    static String toGetterName(String value) {
        return "get" + Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }
}
