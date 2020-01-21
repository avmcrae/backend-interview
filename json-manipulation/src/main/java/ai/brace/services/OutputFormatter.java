package ai.brace.services;

import java.io.PrintStream;
import java.util.List;

public class OutputFormatter {
    private PrintStream outputStream;

    public OutputFormatter(PrintStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputFormatter() {
        this.outputStream = System.out;
    }

    public void printFormattedOutput(List<String> textList) {
        textList.forEach(outputStream::println);
    }
}
