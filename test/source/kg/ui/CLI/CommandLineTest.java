package kg.ui.CLI;

import kg.ui.doubles.FailingInputStreamDouble;
import kg.ui.doubles.FailingOutputStreamDouble;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;

public class CommandLineTest {

    private ByteArrayOutputStream output;

    private ByteArrayInputStream inputStream(String inputString) {
        return new ByteArrayInputStream(inputString.getBytes());
    }

    private CommandLine setupCommandLine(String inputString) {
        return new CommandLine(inputStream(inputString), output);
    }

    @Before
    public void setUp() {
        output = new ByteArrayOutputStream();
    }

    @Test
    public void printsToTheOutput() {
        CommandLine commandLine = setupCommandLine("irrelevant");

        commandLine.show("output");

        assertEquals("output", output.toString());
    }

    @Test
    public void getsInput() {
        CommandLine commandLine = setupCommandLine("input");

        String userInput = commandLine.getInput();

        assertEquals("input", userInput);
    }

    @Test(expected = RuntimeException.class)
    public void throwsRuntimeExceptionOnOutputWrite() {
        OutputStream output = new FailingOutputStreamDouble();
        CommandLine commandLine = new CommandLine(inputStream("irrelevant"), output);

        commandLine.show("");
    }

    @Test(expected = RuntimeException.class)
    public void throwsRuntimeExceptionOnInputRead() {
        InputStream input = new FailingInputStreamDouble();
        CommandLine commandLine = new CommandLine(input, output);

        commandLine.getInput();
    }
}