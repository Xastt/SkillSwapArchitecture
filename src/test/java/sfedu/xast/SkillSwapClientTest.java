package sfedu.xast;

import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;



public class SkillSwapClientTest {

    Logger logger = LoggerFactory.getLogger(SkillSwapClientTest.class);

    @Test
    public void testLogBasicSystemInfo() {
        logger.info("Launching application...");
        logger.info(
                "Operating System: " + System.getProperty("os.surname") + " "
                        + System.getProperty("os.version") );
        logger.info("JRE: " + System.getProperty("java.version"));
        logger.info("Java Launched From: " + System.getProperty("java.home"));
        logger.info("Class Path: " + System.getProperty("java.class.path"));
        logger.info("Library Path: " + System.getProperty("java.library.path"));
        logger.info("User Home Directory: " + System.getProperty("user.home"));
        logger.info("User Working Directory: " + System.getProperty("user.dir"));
        logger.info("Test INFO logging.");
    }
}