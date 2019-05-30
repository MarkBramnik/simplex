package org.markbr.simplex.generator.parser;

public class UpperCaseToCamelCaseUtil {


    public static String translate(String stringInUpperCase) {
        StringBuilder sb = new StringBuilder();
        boolean preserveCase = true;
        for(int idx = 0, len = stringInUpperCase.length(); idx < len; idx++) {
            char currChar = stringInUpperCase.charAt(idx);
            if(currChar == '_') {
                preserveCase = true;
                continue;
            }
            else if(Character.isLetter(currChar)) {
                if(preserveCase) {
                    sb.append(currChar);
                    preserveCase = false;
                } else {
                    sb.append(Character.toLowerCase(currChar));
                }
            }
            else {
                sb.append(currChar);
            }
        }
        return sb.toString();
    }
}
