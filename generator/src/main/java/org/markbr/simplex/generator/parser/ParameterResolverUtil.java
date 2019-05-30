package org.markbr.simplex.generator.parser;

import org.markbr.simplex.generator.sourcecode.DataField;
import org.markbr.simplex.generator.sourcecode.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterResolverUtil {
    private final static Pattern pattern = Pattern.compile("\\{\\{(.*?):(.*?)}}");
    public static List<DataField> resolveParams(String message) {
        Matcher matcher = pattern.matcher(message);
        List<DataField> result = new ArrayList<>();
        while(matcher.find()) {
            String rawParamName = matcher.group(1).trim();
            String rawParamType = matcher.group(2).trim();
            result.add(new DataField(rawParamName, DataType.fromString(rawParamType)));
        }
        return result;
    }

    public static String transformToInterpolatedString(String message) {
        Matcher matcher = pattern.matcher(message);
        StringBuffer sb = new StringBuffer(message.length() + 2);
        sb.append("\"");
        while (matcher.find()) {
            String text = matcher.group(1);
            matcher.appendReplacement(sb, Matcher.quoteReplacement("\"+ " + text +" +\"" ));
        }
        matcher.appendTail(sb);
        sb.append("\"");
        if(sb.length() > 4) {
            String lastChars = sb.substring(sb.length() - 4, sb.length());
            if (lastChars.equals(" +\"\"")) {
                sb.delete(sb.length() - 4, sb.length() );
            }
        }
        if(sb.length() > 4) {
            String firstChars = sb.substring(0, 4);
            if(firstChars.equals("\"\"+ ")) {
                sb.delete(1, 4);
            }
        }

        return sb.toString();
    }
}
