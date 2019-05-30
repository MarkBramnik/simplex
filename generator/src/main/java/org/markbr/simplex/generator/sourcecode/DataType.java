package org.markbr.simplex.generator.sourcecode;

public enum DataType {
    STRING(String.class),
    INT_PRIMITIVE(int.class),
    INTEGER(Integer.class),
    FLOAT(Float.class),
    FLOAT_PRIMITIVE(float.class),
    BOOLEAN(Boolean.class),
    BOOLEAN_PRIMITIVE(boolean.class);

    private Class<?> type;

    DataType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getFieldType() {
        return type;
    }

    public static DataType fromString(String s){
        switch (s) {
            case "int"     : return INT_PRIMITIVE;
            case "Integer" :
            case "Int"     :
                             return INTEGER;
            case "float"   : return FLOAT_PRIMITIVE;
            case "Float"   : return FLOAT;
            case "boolean" : return BOOLEAN_PRIMITIVE;
            case "Boolean" : return BOOLEAN;
            default        : return STRING;
        }
    }
}
