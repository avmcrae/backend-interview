package ai.brace.services;

import ai.brace.models.TextData;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

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

    @Test
    public void shouldMapWordsByFrequencyBeingCaseInsensitive() {
        List<TextData> textDataList = asList(
                new TextData(3, "Some instance"),
                new TextData(1, "Of some word"),
                new TextData(2, "The word instance again")
        );

        Map<String, Integer> wordByFrequency = dataParser.mapWordsByFrequency(textDataList);

        assertThat(wordByFrequency.get("some"), Matchers.equalTo(2));
        assertThat(wordByFrequency.get("instance"), Matchers.equalTo(2));
        assertThat(wordByFrequency.get("of"), Matchers.equalTo(1));
        assertThat(wordByFrequency.get("word"), Matchers.equalTo(2));
        assertThat(wordByFrequency.get("the"), Matchers.equalTo(1));
        assertThat(wordByFrequency.get("again"), Matchers.equalTo(1));
    }

    @Test
    public void shouldFilterOutPunctuationWhenCountingWordFrequency() {
        List<TextData> textDataList = asList(
                new TextData(2, "This word?"),
                new TextData(1, "This word.. again!")
        );

        Map<String, Integer> wordByFrequency = dataParser.mapWordsByFrequency(textDataList);

        assertThat(wordByFrequency.get("word"), Matchers.equalTo(2));
        assertThat(wordByFrequency.get("this"), Matchers.equalTo(2));
        assertThat(wordByFrequency.get("again"), Matchers.equalTo(1));
    }
}