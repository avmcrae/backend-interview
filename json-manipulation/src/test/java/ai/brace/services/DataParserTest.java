package ai.brace.services;

import ai.brace.models.TextData;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;

public class DataParserTest {
    DataParser dataParser;

    @Before
    public void setUp() throws Exception {
        dataParser = new DataParser();
    }

    @Test
    public void shouldMapListOfTextDataToTextDataValue() {
        List<TextData> textDataList = asList(
                new TextData(3, "Data three"),
                new TextData(1, "Data one"),
                new TextData(2, "Data two")
        );

        List<String> sortedText = dataParser.sortByAscendingIds(textDataList);

        assertThat(sortedText, Matchers.containsInAnyOrder(
                "Data one", "Data two", "Data three"
        ));
    }

    @Test
    public void shouldSortResultsByAscendingIds() {
        List<TextData> textDataList = asList(
                new TextData(3, "Data three"),
                new TextData(1, "Data one"),
                new TextData(2, "Data two")
        );

        List<String> sortedText = dataParser.sortByAscendingIds(textDataList);

        assertThat(sortedText, Matchers.equalTo(
                asList("Data one", "Data two", "Data three")
        ));
    }
}