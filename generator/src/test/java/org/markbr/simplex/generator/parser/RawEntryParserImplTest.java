package org.markbr.simplex.generator.parser;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.markbr.simplex.generator.data.Metadata;
import org.markbr.simplex.generator.data.RawYamlEntry;
import testutils.TestDataFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RawEntryParserImplTest {

    @Test
    @DisplayName("sample data is parsed correctly")
    public void testDataParsedCorrectly() {
        RawYamlEntry entry = new RawYamlEntry("TOO_LARGE_ID", 1000, 400, "The Field has value {{value:Integer}} which is more than maximum allowed value of {{maxValue:Integer}}");
        Metadata metadata = new Metadata("com.sample.exceptions", "com.sample.project", "BaseProjectException", false);

        ParsedExceptionData expected = TestDataFactory.prepareTestParsedExceptionData();

        RawEntryParserImpl underTest = new RawEntryParserImpl();
        ParsedExceptionData actual = underTest.parseData(metadata, entry);

        assertEquals(expected, actual);

    }

}