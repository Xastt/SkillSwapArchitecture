package sfedu.xast.api;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;
import sfedu.xast.models.PersInf;

import java.io.IOException;

public class DataProviderCsvTest{

    private DataProviderCsv dataProviderCsv;

    @BeforeEach
    void setUp() {
        dataProviderCsv = new DataProviderCsv();
    }

    @Test
    void testCRUDMethodsWithPersInfPositiveCSV() throws IOException, CsvException {
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        dataProviderCsv.createPersInf(persInf);
        persInf.setName("Updated Name");
        dataProviderCsv.updatePersInf(persInf);
        dataProviderCsv.deletePersInf(persInf.getId());
    }
}

