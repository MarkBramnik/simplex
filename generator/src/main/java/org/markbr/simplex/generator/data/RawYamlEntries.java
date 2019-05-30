package org.markbr.simplex.generator.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RawYamlEntries implements Iterable<RawYamlEntry> {
    private List<RawYamlEntry> rawYamlEntries;

    @Override
    public Iterator<RawYamlEntry> iterator() {
        return rawYamlEntries.iterator();
    }
}
