package ai.brace.services;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class OutputFormatterTest {
    private ByteArrayOutputStream testOutputStream;
    private OutputFormatter outputFormatter;

    @Before
    public void setUp() {
        testOutputStream = new ByteArrayOutputStream();
        outputFormatter = new OutputFormatter(new PrintStream(testOutputStream));
    }

    @Test
    public void shouldPrintInputStringsUsingInjectedPrintStream() {
        List<String> inputStrings = asList("an input one", "another input");
        outputFormatter.printFormattedOutput(inputStrings);

        assertThat(testOutputStream.toString(), is("an input one\nanother input\n"));
    }
}