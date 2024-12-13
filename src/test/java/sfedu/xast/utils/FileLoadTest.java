package sfedu.xast.utils;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sfedu.xast.exceptions.FileLoadErrorException;

import static org.junit.jupiter.api.Assertions.*;

class FileLoadTest {

    Logger logger = LoggerFactory.getLogger(FileLoadTest.class);
    private final FileLoad fileLoad = new FileLoad();

    @Test
    public void checkValidFileName() {
        String validFileName = "src/test/resources/test.txt";
        try {
            fileLoad.loadFile(validFileName);
        } catch (FileLoadErrorException e) {
            logger.error(e.getMessage());
        }
    }

    @Test
    public void checkInValidFileName() {
        String validFileName = "InValid.txt";
        assertThrows(FileLoadErrorException.class,
                () -> fileLoad.loadFile(validFileName));
    }


}