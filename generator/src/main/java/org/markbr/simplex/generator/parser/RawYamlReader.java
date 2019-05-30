package org.markbr.simplex.generator.parser;

import org.markbr.simplex.generator.source.Source;
import org.markbr.simplex.generator.data.RawYamlEntries;

public interface RawYamlReader {
    RawYamlEntries readYaml(Source source);
}
