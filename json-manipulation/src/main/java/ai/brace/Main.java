package ai.brace;

import ai.brace.services.DataParser;
import ai.brace.services.DataPrinterService;
import ai.brace.services.FileLoader;
import ai.brace.services.OutputFormatter;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) {
        FileLoader fileLoader = new FileLoader();
        DataParser dataParser = new DataParser();
        OutputFormatter outputFormatter = new OutputFormatter();

        try {
            DataPrinterService dataPrinterService = new DataPrinterService(fileLoader, dataParser, outputFormatter);
            System.out.println("\nTask one...\n");
            dataPrinterService.printTextGivenFilename("a1.json");

            System.out.println("\nTask two...\n");
            dataPrinterService.printMergedTextGivenFilenames(asList("a1.json", "a2.json"));
        } catch(FileNotFoundException e) {
            System.err.println(String.format("File name not found. Please make sure your file exists and is located in the resources directory. File information: %s", e.getMessage()));
        } catch(JsonSyntaxException e) {
            System.err.println("Invalid JSON format for input file");
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
