package ai.brace.services;

import ai.brace.models.TextData;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class DataPrinterService {
    private FileLoader fileLoader;
    private DataParser dataParser;
    private OutputFormatter outputFormatter;
    private FileValidationService fileValidationService;

    public DataPrinterService(FileLoader fileLoader, DataParser dataParser, OutputFormatter outputFormatter, FileValidationService fileValidationService) {
        this.fileLoader = fileLoader;
        this.dataParser = dataParser;
        this.outputFormatter = outputFormatter;
        this.fileValidationService = fileValidationService;
    }

    public void printSortedTextGivenFilename(String filename) {
        try {
            List<TextData> textData = fileLoader.loadDataFromFile(filename);
            printTextSortedById(textData);
        } catch (Exception e) {
            fileValidationService.validate(e);
        }
    }

    public void printMergedTextGivenFilenames(List<String> filenames) {
        try {
            List<TextData> dataFromAllFiles = getTextDataForListOfFiles(filenames);
            printTextSortedById(dataFromAllFiles);
        } catch (Exception e) {
            fileValidationService.validate(e);
        }
    }

    public void printWordFrequencyGivenFilenames(List<String> filenames) {
        try {
            List<TextData> textData = getTextDataForListOfFiles(filenames);
            Map<String, Integer> wordFrequencies = dataParser.mapWordsByFrequency(textData);
            outputFormatter.printMapValues(wordFrequencies);
        } catch (Exception e) {
            fileValidationService.validate(e);
        }
    }

    private List<TextData> getTextDataForListOfFiles(List<String> filenames) {
        return filenames
                        .stream()
                        .map(filename -> {
                            {
                                try {
                                    return fileLoader.loadDataFromFile(filename);
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e.getMessage());
                                }
                            }
                        }).flatMap(List::stream).collect(toList());
    }

    private void printTextSortedById(List<TextData> textData) {
        List<String> sortedText = dataParser.sortByAscendingIds(textData);
        outputFormatter.printFormattedOutput(sortedText);
    }
}
