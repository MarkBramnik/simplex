package org.markbr.simplex.generator;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.markbr.simplex.generator.data.RawYamlEntries;
import org.markbr.simplex.generator.data.RawYamlEntry;
import org.markbr.simplex.generator.parser.RawYamlReaderImpl;
import org.markbr.simplex.generator.source.ClassPathSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RawYamlReaderTest {

    @Test
    @DisplayName("Read Sample Exception File And Create Raw Data")
    public void test_reads_series_of_exceptions_information() {
        // given:
        RawYamlReaderImpl underTest = new RawYamlReaderImpl();
        // when:
        RawYamlEntries actual = underTest.readYaml(new ClassPathSource("sampleInput.yaml"));
        // then:
        // use hamcrest here
        assertAll(
                () -> assertEquals(2, actual.getRawYamlEntries().size()),
                () -> assertEquals(actual.getRawYamlEntries().get(0), new RawYamlEntry(
                        "TOO_LARGE_ID",
                        1000,
                        400,
                        "The Field has value {{value:Integer}} which is more than maximum allowed value of {{maxValue:Integer}}")),
                () -> assertEquals(actual.getRawYamlEntries().get(1),new RawYamlEntry(
                "SOMETHING_BAD_HAPPENED",
                1001,
                0,
                "Something bad happened"))
        );
    }
}
