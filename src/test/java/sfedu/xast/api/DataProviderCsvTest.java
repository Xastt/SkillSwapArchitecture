package sfedu.xast.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import sfedu.xast.models.PersForApi;

/*public class DataProviderCsvTest{

    private DataProviderCsv dataProviderCsv;
    private String testCsvFilePath = "src/test/resources/test.csv";

    @BeforeEach
    public void setUp() {
        dataProviderCsv = new DataProviderCsv(testCsvFilePath);
    }

    //@AfterEach
    //public void tearDown() {
      //  File file = new File(testCsvFilePath);
        //if (file.exists()) {
          //  file.delete();
        //}
    //}

    @Test
    public void testSaveRecord(){
        PersForApi record = new PersForApi(1L, "Test1");
        dataProviderCsv.saveRecord(record);
        PersForApi retrieved = dataProviderCsv.getRecordById(1L);
        assertNotNull(retrieved);
        assertEquals("Test1", retrieved.getName());
    }

    @Test
    public void testGetRecordById(){
        PersForApi record = new PersForApi(1L, "Test1");
        dataProviderCsv.saveRecord(record);
        PersForApi retrieved = dataProviderCsv.getRecordById(1L);
        assertNotNull(retrieved);
        assertEquals(1L, retrieved.getId());
        assertEquals("Test1", retrieved.getName());
    }

    @Test
    public void testDeleteRecord(){
        PersForApi record = new PersForApi(1L, "Test1");
        dataProviderCsv.saveRecord(record);
        dataProviderCsv.deleteRecord(1L);
        PersForApi retrieved = dataProviderCsv.getRecordById(1L);
        assertNull(retrieved);
    }

}*/