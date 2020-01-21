package ai.brace;

import ai.brace.services.*;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) {
        FileLoader fileLoader = new FileLoader();
        DataProcessingService dataProcessingService = new DataProcessingService();
        OutputFormatter outputFormatter = new OutputFormatter();
        ValidationService validationService = new ValidationService(outputFormatter);

        DataPrinterService dataPrinterService = new DataPrinterService(fileLoader, dataProcessingService, outputFormatter, validationService);
        System.out.println("\nTask one...\n");
        dataPrinterService.printSortedTextGivenFilename("a1.json");

        System.out.println("\nTask two...\n");
        dataPrinterService.printMergedTextGivenFilenames(asList("a1.json", "a2.json"));

        System.out.println("\nTask three...\n");
        dataPrinterService.printWordFrequencyGivenFilenames(asList("a1.json", "a2.json"));

        System.out.println("\nTask four...\n");
        JsonUpdateService jsonUpdateService = new JsonUpdateService(fileLoader,validationService);
        jsonUpdateService.createOutputFileWithLatestDateFromFiles(asList("a1.json", "a2.json"));
    }
}
