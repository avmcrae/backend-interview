package ai.brace.services;

import ai.brace.models.TextData;

import java.io.FileNotFoundException;
import java.util.List;

public class DataPrinterService {
    private FileLoader fileLoader;
    private DataParser dataParser;
    private OutputFormatter outputFormatter;

    public DataPrinterService(FileLoader fileLoader, DataParser dataParser, OutputFormatter outputFormatter) {
        this.fileLoader = fileLoader;
        this.dataParser = dataParser;
        this.outputFormatter = outputFormatter;
    }

    public void printTextGivenFilename(String filename) throws FileNotFoundException {
        List<TextData> textData = fileLoader.loadDataFromFile(filename);
        printTextSortedById(textData);
    }

    private void printTextSortedById(List<TextData> textData) {
        List<String> sortedText = dataParser.sortByAscendingIds(textData);
        outputFormatter.printFormattedOutput(sortedText);
    }
}
