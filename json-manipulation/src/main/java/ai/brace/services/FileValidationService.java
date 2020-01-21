package ai.brace.services;

import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;

public class FileValidationService {
    OutputFormatter outputFormatter;

    public FileValidationService(OutputFormatter outputFormatter) {
        this.outputFormatter = outputFormatter;
    }

    public void validate(Exception e) {
        if (e instanceof FileNotFoundException) {
            outputFormatter.printError(String.format("File name not found. Please make sure your file exists and is located in the resources directory. File information: %s", e.getMessage()));
        } else if (e instanceof JsonSyntaxException) {
            outputFormatter.printError(String.format("Invalid JSON format for input file. %s", e.getMessage()));
        } else {
            outputFormatter.printError(e.getMessage());
        }
    }
}
