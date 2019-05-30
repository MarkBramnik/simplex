package org.markbr.simplex.generator.parser;

import org.markbr.simplex.generator.source.Source;
import org.markbr.simplex.generator.data.RawYamlEntries;
import org.markbr.simplex.generator.data.RawYamlEntry;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class RawYamlReaderImpl implements RawYamlReader {

    @Override
    public RawYamlEntries readYaml(Source source) {
        Yaml yaml = new Yaml(new Constructor(RawYamlEntry.class));
        Iterable<Object> objects = yaml.loadAll(source.getContent());
        List<RawYamlEntry> entries =
                StreamSupport.stream(objects.spliterator(), false)
                .map(this::convertEntry)
                .collect(toList());
        return new RawYamlEntries(entries);
    }

    private RawYamlEntry convertEntry(Object o) {
        return (RawYamlEntry)o;
    }

}
