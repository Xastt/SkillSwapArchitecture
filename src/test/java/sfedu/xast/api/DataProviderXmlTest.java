package sfedu.xast.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import sfedu.xast.models.PersInf;
import sfedu.xast.models.PersInfList;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderXmlTest {

    private DataProviderXml dataProviderXml;
    private String testXmlFilePath = "test.xml";

    @BeforeEach
    void setUp() {
        dataProviderXml = new DataProviderXml(testXmlFilePath);
    }

    @AfterEach
    void tearDown() {
        File file = new File(testXmlFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void saveRecordTest() {
        PersInf record = new PersInf(1L, "Test1");
        dataProviderXml.saveRecord(record);
        PersInf retrievedRecord = dataProviderXml.getRecordById(1L);
        assertNotNull(retrievedRecord);
        assertEquals("Test1", retrievedRecord.getName());
    }

    @Test
    void deleteRecordTest() {
        PersInf record = new PersInf(1L, "Test1");
        dataProviderXml.saveRecord(record);
        dataProviderXml.deleteRecord(1L);
        assertNull(dataProviderXml.getRecordById(1L));
    }

    @Test
    void getRecordById() {
        PersInf record = new PersInf(1L, "Test1");
        dataProviderXml.saveRecord(record);
        PersInf retrievedRecord = dataProviderXml.getRecordById(1L);
        assertNotNull(retrievedRecord);
        assertEquals("Test1", retrievedRecord.getName());
        assertEquals(1L, retrievedRecord.getId());
    }
}