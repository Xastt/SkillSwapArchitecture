package sfedu.xast.api;

import org.junit.jupiter.api.*;
import sfedu.xast.models.PersForApi;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderXmlTest {

    private DataProviderXml dataProviderXml;
    private String testXmlFilePath = "src/test/resources/test.xml";

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
        PersForApi record = new PersForApi(1L, "Test1");
        dataProviderXml.saveRecord(record);
        PersForApi retrievedRecord = dataProviderXml.getRecordById(1L);
        assertNotNull(retrievedRecord);
        assertEquals("Test1", retrievedRecord.getName());
    }

    @Test
    void deleteRecordTest() {
        PersForApi record = new PersForApi(1L, "Test1");
        dataProviderXml.saveRecord(record);
        dataProviderXml.deleteRecord(1L);
        assertNull(dataProviderXml.getRecordById(1L));
    }

    @Test
    void getRecordById() {
        PersForApi record = new PersForApi(1L, "Test1");
        dataProviderXml.saveRecord(record);
        PersForApi retrievedRecord = dataProviderXml.getRecordById(1L);
        assertNotNull(retrievedRecord);
        assertEquals("Test1", retrievedRecord.getName());
        assertEquals(1L, retrievedRecord.getId());
    }
}