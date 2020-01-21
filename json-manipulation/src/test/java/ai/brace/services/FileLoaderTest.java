package ai.brace.services;

import ai.brace.models.TextData;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertThat;

public class FileLoaderTest {

    FileLoader fileLoader = new FileLoader();

    @Test
    public void shouldLoadFileByFilename() throws FileNotFoundException {
        List<TextData> textDataList = fileLoader.loadDataFromFile("test.json");
        assertThat(textDataList, Matchers.hasSize(1));
        assertThat(textDataList.get(0).getTextdata(), Matchers.equalTo("test data"));
    }

}
