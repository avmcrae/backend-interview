package ai.brace.services;

import ai.brace.models.TextData;

import java.io.FileNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    public void printMergedTextGivenFilenames(List<String> filenames) throws FileNotFoundException {
        List<TextData> dataFromAllFiles = filenames
                .stream()
                .map(filename ->
                {
                    try {
                        return fileLoader.loadDataFromFile(filename);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
        ).flatMap(List::stream).collect(toList());
        printTextSortedById(dataFromAllFiles);
    }

    private void printTextSortedById(List<TextData> textData) {
        List<String> sortedText = dataParser.sortByAscendingIds(textData);
        outputFormatter.printFormattedOutput(sortedText);
    }
}
