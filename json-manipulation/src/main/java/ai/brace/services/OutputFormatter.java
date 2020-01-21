package ai.brace.services;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

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

    public void printMapValues(Map<String, Integer> wordFrequencies) {
        wordFrequencies.forEach((key, value) -> outputStream.println(String.format("(%s) : %d", key, value)));
    }
}
