package ai.brace.services;

import ai.brace.models.TextData;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class DataPrinterService {
    private FileLoader fileLoader;
    private DataProcessingService dataProcessingService;
    private OutputFormatter outputFormatter;
    private ValidationService validationService;

    public DataPrinterService(FileLoader fileLoader, DataProcessingService dataProcessingService, OutputFormatter outputFormatter, ValidationService validationService) {
        this.fileLoader = fileLoader;
        this.dataProcessingService = dataProcessingService;
        this.outputFormatter = outputFormatter;
        this.validationService = validationService;
    }

    public void printSortedTextGivenFilename(String filename) {
        try {
            List<TextData> textData = fileLoader.loadDataFromFile(filename).getTextArray();
            printTextSortedById(textData);
        } catch (Exception e) {
            validationService.validate(e);
        }
    }

    public void printMergedTextGivenFilenames(List<String> filenames) {
        try {
            List<TextData> dataFromAllFiles = getTextDataForListOfFiles(filenames);
            printTextSortedById(dataFromAllFiles);
        } catch (Exception e) {
            validationService.validate(e);
        }
    }

    public void printWordFrequencyGivenFilenames(List<String> filenames) {
        try {
            List<TextData> textData = getTextDataForListOfFiles(filenames);
            Map<String, Integer> wordFrequencies = dataProcessingService.mapWordsByFrequency(textData);
            outputFormatter.printMapValues(wordFrequencies);
        } catch (Exception e) {
            validationService.validate(e);
        }
    }

    private List<TextData> getTextDataForListOfFiles(List<String> filenames) {
        return filenames
                        .stream()
                        .map(filename -> {
                            {
                                try {
                                    return fileLoader.loadDataFromFile(filename).getTextArray();
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e.getMessage());
                                }
                            }
                        }).flatMap(List::stream).collect(toList());
    }

    private void printTextSortedById(List<TextData> textData) {
        List<String> sortedText = dataProcessingService.sortByAscendingIds(textData);
        outputFormatter.printFormattedOutput(sortedText);
    }
}
