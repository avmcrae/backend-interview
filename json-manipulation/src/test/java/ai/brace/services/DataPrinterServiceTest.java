package ai.brace.services;

import ai.brace.models.TextData;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

public class DataPrinterServiceTest {

    private DataParser mockDataParser;
    private FileLoader mockFileLoader;
    private OutputFormatter mockOutputFormatter;
    private FileValidationService mockFileValidationService;

    private DataPrinterService dataPrinterService;

    @Before
    public void setUp() {
        mockDataParser = mock(DataParser.class);
        mockFileLoader = mock(FileLoader.class);
        mockOutputFormatter = mock(OutputFormatter.class);
        mockFileValidationService = mock(FileValidationService.class);
        dataPrinterService = new DataPrinterService(mockFileLoader, mockDataParser, mockOutputFormatter, mockFileValidationService);
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

    // TODO: refactor to avoid using any (use argument captor?)
    @Test
    public void shouldMergeTextDataListsWhenMultipleFilenamesArePassedIn() throws FileNotFoundException {
        String filenameOne = "testfile.json";
        String filenameTwo = "testfile2.json";

        List<TextData> textDataFromFileOne = asList(new TextData(1, "data one"), new TextData(3, "data three"));
        List<TextData> textDataFromFileTwo = singletonList(new TextData(2, "data two"));

        when(mockFileLoader.loadDataFromFile(filenameOne)).thenReturn(textDataFromFileOne);
        when(mockFileLoader.loadDataFromFile(filenameTwo)).thenReturn(textDataFromFileTwo);

        List<String> expectedStringList = asList("data one", "data two", "text three");
        when(mockDataParser.sortByAscendingIds(any())).thenReturn(expectedStringList);

        dataPrinterService.printMergedTextGivenFilenames(asList(filenameTwo, filenameOne));

        verify(mockOutputFormatter).printFormattedOutput(expectedStringList);
    }
}