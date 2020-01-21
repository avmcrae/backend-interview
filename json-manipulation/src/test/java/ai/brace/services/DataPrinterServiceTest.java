package ai.brace.services;

import ai.brace.models.TextData;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DataPrinterServiceTest {

    private DataParser mockDataParser;
    private FileLoader mockFileLoader;
    private OutputFormatter mockOutputFormatter;

    private DataPrinterService dataPrinterService;

    @Before
    public void setUp() {
        mockDataParser = mock(DataParser.class);
        mockFileLoader = mock(FileLoader.class);
        mockOutputFormatter = mock(OutputFormatter.class);
        dataPrinterService = new DataPrinterService(mockFileLoader, mockDataParser, mockOutputFormatter);
    }

    @Test
    public void shouldCallOutputFormatterWithResultOfDataParser() throws FileNotFoundException {
        String filename = "testfile.json";
        List<TextData> textDataFromFile = asList(new TextData(1, "data one"), new TextData(3, "data three"), new TextData(2, "data two"));
        List<String> expectedStringList = asList("data one", "data two", "text three");
        when(mockFileLoader.loadDataFromFile(filename)).thenReturn(textDataFromFile);
        when(mockDataParser.sortByAscendingIds(textDataFromFile)).thenReturn(expectedStringList);

        dataPrinterService.printTextGivenFilename(filename);

        verify(mockOutputFormatter).printFormattedOutput(expectedStringList);
    }
}