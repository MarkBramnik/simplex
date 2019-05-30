package org.markbr.simplex.generator.parser;

import org.markbr.simplex.generator.data.Metadata;
import org.markbr.simplex.generator.data.RawYamlEntry;
import org.markbr.simplex.generator.sourcecode.DataField;

import java.util.List;

public class RawEntryParserImpl implements RawEntryParser {
    @Override
    public ParsedExceptionData parseData(Metadata metadata, RawYamlEntry rawEntry) {

        ParsedExceptionData.ParsedExceptionDataBuilder resultBuilder = ParsedExceptionData.builder();

        return
            resultBuilder.packageName       (       resolvePackageName(metadata) )
                         .exceptionClassName(       resolveExceptionClassName(rawEntry))
                         .baseExceptionPackageName( resolveBaseExceptionPackageName(metadata))
                         .baseExceptionName (       resolveBaseExceptionName(metadata))
                         .httpStatus        (       resolveHttpStatus(rawEntry))
                         .code              (       resolveCode(rawEntry))
                         .params            (       resolveParams(rawEntry))
                         .rawMessage        (       resolveRawMessage(rawEntry))
                         .build();

    }

    private String resolvePackageName(Metadata metadata) {
        return metadata.getExceptionsPackageName();
    }

    private String resolveBaseExceptionName(Metadata metadata) {
        return metadata.getBaseExceptionClassName();
    }

    private String resolveBaseExceptionPackageName(Metadata metadata) {
        return metadata.getBaseExceptionPackageName();
    }

    private String resolveExceptionClassName(RawYamlEntry rawEntry) {
        String id = rawEntry.getId();
        return UpperCaseToCamelCaseUtil.translate(id) + "Exception";
    }

    private int resolveHttpStatus(RawYamlEntry rawEntry) {
        return rawEntry.getHttpStatus();
    }

    private int resolveCode(RawYamlEntry rawEntry) {
        return rawEntry.getErrorCode();
    }

    private List<DataField> resolveParams(RawYamlEntry rawEntry) {
        return ParameterResolverUtil.resolveParams(rawEntry.getMessage());
    }

    private String resolveRawMessage(RawYamlEntry rawEntry) {
        return rawEntry.getMessage();
    }
}
