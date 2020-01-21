package ai.brace.services;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class OutputFormatterTest {
    private ByteArrayOutputStream testOutputStream;
    private ByteArrayOutputStream testErrorStream;
    private OutputFormatter outputFormatter;

    @Before
    public void setUp() {
        testOutputStream = new ByteArrayOutputStream();
        testErrorStream = new ByteArrayOutputStream();
        outputFormatter = new OutputFormatter(new PrintStream(testOutputStream), new PrintStream(testErrorStream));
    }

    @Test
    public void shouldPrintInputStringsUsingInjectedPrintStream() {
        List<String> inputStrings = asList("an input one", "another input");
        outputFormatter.printFormattedOutput(inputStrings);

        assertThat(testOutputStream.toString(), is("an input one\nanother input\n"));
    }

    @Test
    public void shouldPrintToErrorStreamWhenPrintingAnError() {
        String error = "something went wrong";
        outputFormatter.printError(error);

        assertThat(testErrorStream.toString(), is(String.format("%s\n", error)));
    }

    @Test
    public void shouldPrintFormattedMapEntriesByKeyAndValue() {
        Map<String, Integer> map = Map.of("Word", 1, "two", 2);
        outputFormatter.printMapValues(map);

        assertThat(testOutputStream.toString(), containsString("(Word) : 1\n"));
        assertThat(testOutputStream.toString(), containsString("(two) : 2\n"));
    }
}