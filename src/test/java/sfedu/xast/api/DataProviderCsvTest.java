package sfedu.xast.api;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;
import sfedu.xast.models.PersInf;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DataProviderCsvTest{

    private DataProviderCsv dataProviderCsv;

    @BeforeEach
    void setUp() {
        dataProviderCsv = new DataProviderCsv();
    }

    @Test
    void testCRUDMethodsWithPersInfPositiveCSV() throws IOException, CsvException {

        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");

        assertTrue(dataProviderCsv.createPersInf(persInf));

        PersInf retrievedUser = dataProviderCsv.readPersInf(persInf, persInf.getId());
        assertNotNull(retrievedUser);
        assertEquals("Surname", retrievedUser.getSurname());
        assertEquals("Name", retrievedUser.getName());
        assertEquals("PhoneNumber", retrievedUser.getPhoneNumber());
        assertEquals("Email", retrievedUser.getEmail());

        retrievedUser.setSurname("Updated Surname");
        retrievedUser.setName("Updated Name");
        assertTrue(dataProviderCsv.updatePersInf(retrievedUser));

        PersInf updatedUser = dataProviderCsv.readPersInf(retrievedUser, retrievedUser.getId());
        assertNotNull(updatedUser);
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("Updated Surname", updatedUser.getSurname());

        assertTrue(dataProviderCsv.deletePersInf(persInf.getId()));
    }

    @Test
    void testCRUDMethodsWithPersInfNegativeCSV() throws IOException, CsvException {

        //CreatePersInfWithNull
        PersInf persInf = null;
        assertFalse(dataProviderCsv.createPersInf(persInf));

        //ReadPersInfWithNull
        String invalidId = "666";
        CsvException exception = assertThrows(CsvException.class, () -> {
            dataProviderCsv.readPersInf(persInf, invalidId);
        });
        assertEquals("PersInf object must not be null", exception.getMessage());

        //UpdatePersInfWithNull
        CsvException exceptionNew = assertThrows(CsvException.class, () -> {
            dataProviderCsv.updatePersInf(persInf);
        });
        assertEquals("PersInf object must not be null", exceptionNew.getMessage());

        //DeletePersInfWithNullId
        String id = null;
        boolean res = dataProviderCsv.deletePersInf(id);
        assertFalse(res);
    }
}

