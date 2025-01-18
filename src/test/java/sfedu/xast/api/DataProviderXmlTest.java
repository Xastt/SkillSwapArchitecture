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

    @Test
    void testCRUDMethodsWithProfInfPositiveXML() throws IOException, ParserConfigurationException, XMLParseException, SAXException {
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);

        assertTrue(dataProviderXml.createPersInf(persInf));
        assertTrue(dataProviderXml.createProfInf(profInf, persInf));

        ProfInf retrievedUser = dataProviderXml.readProfInf(profInf, persInf.getId());
        assertNotNull(retrievedUser);
        assertEquals("Programming", retrievedUser.getSkillName());
        assertEquals("Programming in Java", retrievedUser.getSkillDescription());
        assertEquals(2500.00, retrievedUser.getCost());
        assertEquals("Java backend developer", retrievedUser.getPersDescription());
        assertEquals(5.5, retrievedUser.getExp());
        assertEquals(4.0, retrievedUser.getRating());

        retrievedUser.setSkillName("UpdatedSkill");
        assertTrue(dataProviderXml.updateProfInf(retrievedUser));

        assertTrue(dataProviderXml.deletePersInf(persInf.getId()));
        assertTrue(dataProviderXml.deleteProfInf(profInf.getPersId()));
    }

    @Test
    void testCRUDMethodsWithProfInfNegativeXML() {

        //CreateWithNull
        PersInf persInf = null;
        ProfInf profInf = null;
        assertFalse(dataProviderXml.createProfInf(profInf, persInf));

        //ReadWithNull
        String invalidId = "666";
        assertThrows(XMLParseException.class, () -> {
            dataProviderXml.readProfInf(profInf, invalidId);
        });

        //UpdateWithNull
        assertFalse(dataProviderXml.updateProfInf(profInf));

        //DeleteWithNullId
        String id = null;
        boolean res = dataProviderXml.deleteProfInf(id);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithSkillExchangePositiveXML() throws IOException, ParserConfigurationException, XMLParseException, SAXException {
        PersInf persInfRequesting = new PersInf("Bober","Curwa","+88005553535", "curwa@mail.ru");
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);
        SkillExchange skillExchange = new SkillExchange(profInf.getSkillName(),persInfRequesting.getId(),profInf.getPersId());

        assertTrue(dataProviderXml.createPersInf(persInfRequesting));
        assertTrue(dataProviderXml.createPersInf(persInf));
        assertTrue(dataProviderXml.createProfInf(profInf, persInf));
        assertTrue(dataProviderXml.createSkillExchange(skillExchange));

        SkillExchange retrievedSkillExchange = dataProviderXml.readSkillExchange(skillExchange);
        assertNotNull(retrievedSkillExchange);
        assertNotNull(retrievedSkillExchange);
        assertEquals(persInfRequesting.getId(), retrievedSkillExchange.getUserRequesting());
        assertEquals(profInf.getPersId(), retrievedSkillExchange.getUserOffering());
        assertEquals(profInf.getSkillName(), retrievedSkillExchange.getSkillOffered());

        retrievedSkillExchange.setSkillOffered("UpdatedSkill");
        dataProviderXml.updateSkillExchange(retrievedSkillExchange);

        SkillExchange updatedSkillExchange = dataProviderXml.readSkillExchange(skillExchange);
        assertEquals("UpdatedSkill", updatedSkillExchange.getSkillOffered());

        assertTrue(dataProviderXml.deletePersInf(persInf.getId()));
        assertTrue(dataProviderXml.deleteProfInf(profInf.getPersId()));
        assertTrue(dataProviderXml.deletePersInf(persInfRequesting.getId()));
        assertTrue(dataProviderXml.deleteSkillExchange(skillExchange.getExchangeId()));
    }

    @Test
    void testCRUDMethodsWithSkillExchangeNegativeXML() {

        //CreateWithNull
        SkillExchange skillExchange = null;
        assertFalse(dataProviderXml.createSkillExchange(skillExchange));

        //ReadWithNull
        assertThrows(XMLParseException.class, () -> {
            dataProviderXml.readSkillExchange(skillExchange);
        });

        //UpdateWithNull
        assertFalse(dataProviderXml.updateSkillExchange(skillExchange));

        //DeleteWithNullId
        String id = null;
        boolean res = dataProviderXml.deleteSkillExchange(id);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithReviewPositiveXML() throws IOException, ParserConfigurationException, XMLParseException, SAXException {
        PersInf persInfReviewer = new PersInf("Bober","Curwa","+88005553535", "curwa@mail.ru");
        PersInf persInfEvaluated = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInfEvaluated.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);
        Review review = new Review(4.5, "Good job!", persInfReviewer.getId(), profInf.getPersId());

        assertTrue(dataProviderXml.createPersInf(persInfReviewer));
        assertTrue(dataProviderXml.createPersInf(persInfEvaluated));
        assertTrue(dataProviderXml.createProfInf(profInf, persInfEvaluated));
        assertTrue(dataProviderXml.createReview(review));

        Review retrievedReview = dataProviderXml.readReview(review);
        assertNotNull(retrievedReview);
        assertEquals(4.5, retrievedReview.getRating());
        assertEquals("Good job!", retrievedReview.getComment());
        assertEquals(persInfReviewer.getId(), retrievedReview.getReviewer());
        assertEquals(profInf.getPersId(), retrievedReview.getUserEvaluated());

        review.setComment("Not God Job!");
        dataProviderXml.updateReview(review);

        Review updatedReview = dataProviderXml.readReview(review);
        assertEquals("Not God Job!", updatedReview.getComment());

        assertTrue(dataProviderXml.deletePersInf(persInfReviewer.getId()));
        assertTrue(dataProviderXml.deleteProfInf(profInf.getPersId()));
        assertTrue(dataProviderXml.deletePersInf(persInfEvaluated.getId()));
        assertTrue(dataProviderXml.deleteReview(review));
    }

    @Test
    void testCRUDMethodsWithReviewNegativeXML() {

        //CreateWithNull
        Review review = null;
        assertFalse(dataProviderXml.createReview(review));

        //ReadWithNull
        assertThrows(XMLParseException.class, () -> {
            dataProviderXml.readReview(review);
        });

        //UpdateWithNull
        assertFalse(dataProviderXml.updateReview(review));

        //DeleteWithNullId
        String id = null;
        boolean res = dataProviderXml.deleteReview(review);
        assertFalse(res);
    }



}