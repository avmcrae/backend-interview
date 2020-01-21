package ai.brace;

import ai.brace.services.*;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) {
        FileLoader fileLoader = new FileLoader();
        DataParser dataParser = new DataParser();
        OutputFormatter outputFormatter = new OutputFormatter();
        FileValidationService fileValidationService = new FileValidationService(outputFormatter);

        DataPrinterService dataPrinterService = new DataPrinterService(fileLoader, dataParser, outputFormatter, fileValidationService);
        System.out.println("\nTask one...\n");
        dataPrinterService.printTextGivenFilename("a1.json");

        System.out.println("\nTask two...\n");
        dataPrinterService.printMergedTextGivenFilenames(asList("a1.json", "a2.json"));
    }
}
