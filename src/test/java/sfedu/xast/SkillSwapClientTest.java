package sfedu.xast;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class SkillSwapClientTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testLogBasicSystemInfo() {
        SkillSwapClient client = new SkillSwapClient();
        client.logBasicSystemInfo();

        String loggedOutput = outputStream.toString();

        assertTrue(loggedOutput.contains("Launching application..."));
        assertTrue(loggedOutput.contains("Operating System:"));
        assertTrue(loggedOutput.contains("JRE:"));
        assertTrue(loggedOutput.contains("Java Launched From:"));
        assertTrue(loggedOutput.contains("Class Path:"));
        assertTrue(loggedOutput.contains("Library Path:"));
        assertTrue(loggedOutput.contains("User Home Directory:"));
        assertTrue(loggedOutput.contains("User Working Directory:"));
        assertTrue(loggedOutput.contains("Test INFO logging."));

    }

    @After
    public void tearDown()  {
        System.setOut(originalOut);
    }

}