package org.markbr.simplex.generator.parser;

import org.markbr.simplex.generator.data.Metadata;
import org.markbr.simplex.generator.data.RawYamlEntry;

public interface RawEntryParser {

    ParsedExceptionData parseData(Metadata metadata, RawYamlEntry rawEntry);

}
