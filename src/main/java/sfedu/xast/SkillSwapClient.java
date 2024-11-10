package sfedu.xast;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkillSwapClient {
    Logger logger = LoggerFactory.getLogger(Main.class);

    //Logger logger = Logger.getLogger(Main.class);

    SkillSwapClient() {
        logger.debug("SkillSwapClient: starting app...");
        logBasicSystemInfo();
    }

    private void logBasicSystemInfo(){
        logger.info("Launching application...");
        logger.info(
                "Operating System: " + System.getProperty("os.name") + " "
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
