package sfedu.xast.api;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sfedu.xast.models.*;
import sfedu.xast.utils.*;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
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

    @Test
    void testCRUDMethodsWithProfInfPositiveCSV() throws IOException, CsvException {
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);

        assertTrue(dataProviderCsv.createPersInf(persInf));
        assertTrue(dataProviderCsv.createProfInf(profInf, persInf));

        ProfInf retrievedUser = dataProviderCsv.readProfInf(profInf, persInf.getId());
        assertNotNull(retrievedUser);
        assertEquals("Programming", retrievedUser.getSkillName());
        assertEquals("Programming in Java", retrievedUser.getSkillDescription());
        assertEquals(2500.00, retrievedUser.getCost());
        assertEquals("Java backend developer", retrievedUser.getPersDescription());
        assertEquals(5.5, retrievedUser.getExp());
        assertEquals(4.0, retrievedUser.getRating());

        retrievedUser.setSkillName("UpdatedSkill");
        assertTrue(dataProviderCsv.updateProfInf(retrievedUser));

        assertTrue(dataProviderCsv.deletePersInf(persInf.getId()));
        assertTrue(dataProviderCsv.deleteProfInf(profInf.getPersId()));
    }

    @Test
    void testCRUDMethodsWithProfInfNegativeCSV() throws IOException, CsvException {

        //CreateProfInfWithNull
        PersInf persInf = null;
        ProfInf profInf = null;
        assertFalse(dataProviderCsv.createProfInf(profInf, persInf));

        //ReadProfInfWithNonExistingId
        String invalidId = "666";
        CsvException exception = assertThrows(CsvException.class, () -> {
            dataProviderCsv.readProfInf(profInf, invalidId);
        });
        assertEquals("ProfInf object must not be null", exception.getMessage());

        //UpdateProfInfWithNull
        CsvException exceptionNew = assertThrows(CsvException.class, () -> {
            dataProviderCsv.updateProfInf(profInf);
        });
        assertEquals("ProfInf object must not be null", exceptionNew.getMessage());

        //DeleteProfInfWithNullId
        String id = null;
        boolean res = dataProviderCsv.deleteProfInf(id);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithSkillExchangePositiveCSV() throws IOException, CsvException {
        PersInf persInfRequesting = new PersInf("Bober","Curwa","+88005553535", "curwa@mail.ru");
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);
        SkillExchange skillExchange = new SkillExchange(profInf.getSkillName(),persInfRequesting.getId(),profInf.getPersId());

        assertTrue(dataProviderCsv.createPersInf(persInfRequesting));
        assertTrue(dataProviderCsv.createPersInf(persInf));
        assertTrue(dataProviderCsv.createProfInf(profInf, persInf));
        assertTrue(dataProviderCsv.createSkillExchange(skillExchange));

        SkillExchange retrievedSkillExchange = dataProviderCsv.readSkillExchange(skillExchange);
        assertNotNull(retrievedSkillExchange);
        assertNotNull(retrievedSkillExchange);
        assertEquals(persInfRequesting.getId(), retrievedSkillExchange.getUserRequesting());
        assertEquals(profInf.getPersId(), retrievedSkillExchange.getUserOffering());
        assertEquals(profInf.getSkillName(), retrievedSkillExchange.getSkillOffered());

        retrievedSkillExchange.setSkillOffered("UpdatedSkill");
        dataProviderCsv.updateSkillExchange(retrievedSkillExchange);

        SkillExchange updatedSkillExchange = dataProviderCsv.readSkillExchange(skillExchange);
        assertEquals("UpdatedSkill", updatedSkillExchange.getSkillOffered());

        assertTrue(dataProviderCsv.deletePersInf(persInf.getId()));
        assertTrue(dataProviderCsv.deleteProfInf(profInf.getPersId()));
        assertTrue(dataProviderCsv.deletePersInf(persInfRequesting.getId()));
        assertTrue(dataProviderCsv.deleteSkillExchange(skillExchange.getExchangeId()));
    }

    @Test
    void testCRUDMethodsWithSkillExchangeNegativeCSV() throws IOException, CsvException {

        //CreateSkillExchangeWithNull
        SkillExchange skillExchange = null;
        assertFalse(dataProviderCsv.createSkillExchange(skillExchange));

        //ReadSkillExchangeWithNonExistingId
        assertThrows(CsvException.class, () -> {
            dataProviderCsv.readSkillExchange(skillExchange);
        });

        //UpdateSkillExchangeWithNull
        CsvException exceptionNew = assertThrows(CsvException.class, () -> {
            dataProviderCsv.updateSkillExchange(skillExchange);
        });
        assertEquals("SkillExchange object must not be null", exceptionNew.getMessage());

        //DeleteSkillExchangeWithNullId
        String id = null;
        boolean res = dataProviderCsv.deleteSkillExchange(id);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithReviewPositiveCSV() throws IOException, CsvException {
        PersInf persInfReviewer = new PersInf("Bober","Curwa","+88005553535", "curwa@mail.ru");
        PersInf persInfEvaluated = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInfEvaluated.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);
        Review review = new Review(4.5, "Good job!", persInfReviewer.getId(), profInf.getPersId());

        assertTrue(dataProviderCsv.createPersInf(persInfReviewer));
        assertTrue(dataProviderCsv.createPersInf(persInfEvaluated));
        assertTrue(dataProviderCsv.createProfInf(profInf, persInfEvaluated));
        assertTrue(dataProviderCsv.createReview(review));

        Review retrievedReview = dataProviderCsv.readReview(review);
        assertNotNull(retrievedReview);
        assertEquals(4.5, retrievedReview.getRating());
        assertEquals("Good job!", retrievedReview.getComment());
        assertEquals(persInfReviewer.getId(), retrievedReview.getReviewer());
        assertEquals(profInf.getPersId(), retrievedReview.getUserEvaluated());

        review.setComment("Not God Job!");
        dataProviderCsv.updateReview(review);

        Review updatedReview = dataProviderCsv.readReview(review);
        assertEquals("Not God Job!", updatedReview.getComment());

        assertTrue(dataProviderCsv.deletePersInf(persInfReviewer.getId()));
        assertTrue(dataProviderCsv.deleteProfInf(profInf.getPersId()));
        assertTrue(dataProviderCsv.deletePersInf(persInfEvaluated.getId()));
        assertTrue(dataProviderCsv.deleteReview(review));
    }

    @Test
    void testCRUDMethodsWithReviewNegativeCSV() throws IOException, CsvException {

        //CreateSkillExchangeWithNull
        Review review = null;
        assertFalse(dataProviderCsv.createReview(review));

        //ReadReviewWithNonExistingId
        assertThrows(CsvException.class, () -> {
            dataProviderCsv.readReview(review);
        });

        //UpdateReviewWithNull
        CsvException exceptionNew = assertThrows(CsvException.class, () -> {
            dataProviderCsv.updateReview(review);
        });
        assertEquals("Review object must not be null", exceptionNew.getMessage());

        //DeleteReviewWithNullId
        String id = null;
        boolean res = dataProviderCsv.deleteReview(review);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithTransactionPositiveCSV() throws IOException, CsvException {
        PersInf persInfRequesting = new PersInf("Bober","Curwa","+88005553535", "curwa@mail.ru");
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);
        SkillExchange skillExchange = new SkillExchange(profInf.getSkillName(),persInfRequesting.getId(),profInf.getPersId());
        Transaction transaction = new Transaction(Status.COMPLETED, skillExchange.getExchangeId());

        assertTrue(dataProviderCsv.createPersInf(persInfRequesting));
        assertTrue(dataProviderCsv.createPersInf(persInf));
        assertTrue(dataProviderCsv.createProfInf(profInf, persInf));
        assertTrue(dataProviderCsv.createSkillExchange(skillExchange));
        assertTrue(dataProviderCsv.createTransaction(transaction));

        Transaction retrievedTransaction = dataProviderCsv.readTransaction(transaction);
        assertNotNull(retrievedTransaction);
        assertEquals(Status.COMPLETED, retrievedTransaction.getStatus());
        assertEquals(skillExchange.getExchangeId(), transaction.getChangeId());

        retrievedTransaction.setStatus(Status.IN_PROCESS);
        dataProviderCsv.updateTransaction(transaction);

        Transaction updatedTransaction = dataProviderCsv.readTransaction(transaction);
        assertEquals(Status.IN_PROCESS, updatedTransaction.getStatus());

        assertTrue(dataProviderCsv.deleteTransaction(transaction));
        assertTrue(dataProviderCsv.deletePersInf(persInf.getId()));
        assertTrue(dataProviderCsv.deleteProfInf(profInf.getPersId()));
        assertTrue(dataProviderCsv.deletePersInf(persInfRequesting.getId()));
        assertTrue(dataProviderCsv.deleteSkillExchange(skillExchange.getExchangeId()));
    }

    @Test
    void testCRUDMethodsWithTransactionNegativeCSV() throws IOException, CsvException {

        //CreateTransactionWithNull
        Transaction transaction = null;
        assertFalse(dataProviderCsv.createTransaction(transaction));

        //ReadTransactionWithNonExistingId
        assertThrows(CsvException.class, () -> {
            dataProviderCsv.readTransaction(transaction);
        });

        //UpdateTransactionWithNull
        CsvException exceptionNew = assertThrows(CsvException.class, () -> {
            dataProviderCsv.updateTransaction(transaction);
        });
        assertEquals("Transaction object must not be null", exceptionNew.getMessage());
    }
}

