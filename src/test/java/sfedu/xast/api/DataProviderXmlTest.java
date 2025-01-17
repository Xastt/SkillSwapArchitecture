package sfedu.xast.api;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;
import org.xml.sax.SAXException;
import sfedu.xast.models.*;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderXmlTest {

    private DataProviderXml dataProviderXml;

    @BeforeEach
    void setUp() {
        dataProviderXml = new DataProviderXml();
    }

    @Test
    void testCRUDMethodsWithPersInfPositiveCSV() throws IOException, ParserConfigurationException, TransformerException, SAXException, XMLParseException {
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");

        assertTrue(dataProviderXml.createPersInf(persInf));

        PersInf retrievedUser = dataProviderXml.readPersInf(persInf, persInf.getId());
        assertNotNull(retrievedUser);
        assertEquals("Surname", retrievedUser.getSurname());
        assertEquals("Name", retrievedUser.getName());
        assertEquals("PhoneNumber", retrievedUser.getPhoneNumber());
        assertEquals("Email", retrievedUser.getEmail());

        retrievedUser.setSurname("Updated Surname");
        retrievedUser.setName("Updated Name");
        assertTrue(dataProviderXml.updatePersInf(retrievedUser));

        PersInf updatedUser = dataProviderXml.readPersInf(retrievedUser, retrievedUser.getId());
        assertNotNull(updatedUser);
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("Updated Surname", updatedUser.getSurname());

        assertTrue(dataProviderXml.deletePersInf(persInf.getId()));
    }

    @Test
    void testCRUDMethodsWithPersInfNegativeXML() throws IOException, CsvException {

        //CreateWithNull
        PersInf persInf = null;
        assertFalse(dataProviderXml.createPersInf(persInf));

        //ReadWithNull
        String invalidId = "666";
        assertThrows(XMLParseException.class, () -> {
            dataProviderXml.readPersInf(persInf, invalidId);
        });

        //UpdateWithNull
        assertFalse(dataProviderXml.updatePersInf(persInf));

        //DeleteWithNullId
        String id = null;
        boolean res = dataProviderXml.deletePersInf(id);
        assertFalse(res);
    }

}