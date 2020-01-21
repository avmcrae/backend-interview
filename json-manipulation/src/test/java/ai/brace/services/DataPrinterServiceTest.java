package ai.brace.services;

import ai.brace.models.InputFileData;
import ai.brace.models.TextData;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.*;

public class DataPrinterServiceTest {

    private DataProcessingService mockDataProcessingService;
    private FileLoader mockFileLoader;
    private OutputFormatter mockOutputFormatter;
    private ValidationService mockValidationService;

    private DataPrinterService dataPrinterService;

    @Before
    public void setUp() {
        mockDataProcessingService = mock(DataProcessingService.class);
        mockFileLoader = mock(FileLoader.class);
        mockOutputFormatter = mock(OutputFormatter.class);
        mockValidationService = mock(ValidationService.class);
        dataPrinterService = new DataPrinterService(mockFileLoader, mockDataProcessingService, mockOutputFormatter, mockValidationService);
    }

    @Test
    public void shouldCallOutputFormatterWithResultOfDataParser() throws FileNotFoundException {
        String filename = "testfile.json";
        List<TextData> textDataFromFile = asList(new TextData(1, "data one"), new TextData(3, "data three"), new TextData(2, "data two"));
        List<String> expectedStringList = asList("data one", "data two", "text three");
        when(mockFileLoader.loadDataFromFile(filename)).thenReturn(new InputFileData(textDataFromFile));
        when(mockDataProcessingService.sortByAscendingIds(textDataFromFile)).thenReturn(expectedStringList);

        dataPrinterService.printSortedTextGivenFilename(filename);

        verify(mockOutputFormatter).printFormattedOutput(expectedStringList);
    }

    // TODO: refactor to avoid using any (use argument captor?)
    @Test
    public void shouldMergeTextDataListsWhenMultipleFilenamesArePassedIn() throws FileNotFoundException {
        String filenameOne = "testfile.json";
        String filenameTwo = "testfile2.json";
        setupMocksForTwoFiles(filenameOne, filenameTwo);

        List<String> expectedStringList = asList("data one", "data two", "text three");
        when(mockDataProcessingService.sortByAscendingIds(any())).thenReturn(expectedStringList);

        dataPrinterService.printMergedTextGivenFilenames(asList(filenameTwo, filenameOne));

        verify(mockOutputFormatter).printFormattedOutput(expectedStringList);
    }

    @Test
    public void shouldPrintWordByFrequency() throws FileNotFoundException {
        String filenameOne = "testfile.json";
        String filenameTwo = "testfile2.json";
        setupMocksForTwoFiles(filenameOne, filenameTwo);

        Map<String, Integer> expectedMap = Map.of("data", 2, "one", 1, "two", 1, "text", 1, "three", 1);
        when(mockDataProcessingService.mapWordsByFrequency(any())).thenReturn(expectedMap);

        dataPrinterService.printWordFrequencyGivenFilenames(asList(filenameTwo, filenameOne));

        verify(mockOutputFormatter).printMapValues(expectedMap);
    }

    private void setupMocksForTwoFiles(String filenameOne, String filenameTwo) throws FileNotFoundException {
        List<TextData> textDataFromFileOne = asList(new TextData(1, "data one"), new TextData(3, "data three"));
        List<TextData> textDataFromFileTwo = singletonList(new TextData(2, "data two"));

        when(mockFileLoader.loadDataFromFile(filenameOne)).thenReturn(new InputFileData(textDataFromFileOne));
        when(mockFileLoader.loadDataFromFile(filenameTwo)).thenReturn(new InputFileData(textDataFromFileTwo));
    }
}