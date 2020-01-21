package ai.brace.services;

import java.io.PrintStream;
import java.util.List;

public class OutputFormatter {
    private PrintStream outputStream;
    private PrintStream errorStream;

    public OutputFormatter(PrintStream outputStream, PrintStream errorStream) {
        this.outputStream = outputStream;
        this.errorStream = errorStream;
    }

    public OutputFormatter() {
        this.outputStream = System.out;
        this.errorStream = System.err;
    }

    public void printFormattedOutput(List<String> textList) {
        textList.forEach(outputStream::println);
    }

    public void printError(String errorText) {
        errorStream.println(errorText);
    }
}
