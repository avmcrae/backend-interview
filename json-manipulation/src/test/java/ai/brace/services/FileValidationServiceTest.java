package ai.brace.services;

import com.google.gson.JsonSyntaxException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FileValidationServiceTest {
    private OutputFormatter outputFormatter;
    private FileValidationService fileValidationService;

    @Before
    public void setUp() {
        outputFormatter = mock(OutputFormatter.class);
        fileValidationService = new FileValidationService(outputFormatter);
    }

    @Test
    public void shouldLogInformationWhenFileNotFound() {
        String exceptionMessage = "File Not Found: abc.json";
        Exception e = new FileNotFoundException(exceptionMessage);

        fileValidationService.validate(e);

        verify(outputFormatter).printError(String.format("File name not found. Please make sure your file exists and is located in the resources directory. File information: %s", exceptionMessage));
    }

    @Test
    public void shouldLogInformationWhenJsonCannotBeProcessed() {
        String exceptionMessage = "Required value textarray";
        Exception e = new JsonSyntaxException(exceptionMessage);

        fileValidationService.validate(e);

        verify(outputFormatter).printError(String.format("Invalid JSON format for input file. %s", exceptionMessage));
    }

    @Test
    public void shouldLogGenericMessageWhenUnexpectedException() {
        String exceptionMessage = "oops!";
        Exception e = new RuntimeException(exceptionMessage);

        fileValidationService.validate(e);

        verify(outputFormatter).printError(exceptionMessage);
    }
}