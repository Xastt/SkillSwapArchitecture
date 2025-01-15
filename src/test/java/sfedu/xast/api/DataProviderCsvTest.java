package sfedu.xast.api;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.*;
import sfedu.xast.models.*;
import sfedu.xast.utils.Constants;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DataProviderCsvTest{

    String sourceCsvPathPersInf = Constants.csvPersInfTestFilePath;
    String sourceCsvPathProfInf = Constants.csvProfInfTestFilePath;
    String sourceCsvPathSkillExchange = Constants.csvSkillExchangeTestFilePath;
    String sourceCsvPathReview = Constants.csvReviewTestFilePath;

    private DataProviderCsv dataProviderCsv;

    @BeforeEach
    void setUp() {
        dataProviderCsv = new DataProviderCsv();
    }

    @Test
    void testCRUDMethodsWithPersInfPositiveCSV() throws IOException, CsvException {
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");

        assertTrue(dataProviderCsv.createPersInf(persInf, sourceCsvPathPersInf));

        PersInf retrievedUser = dataProviderCsv.readPersInf(persInf, persInf.getId(), sourceCsvPathPersInf);
        assertNotNull(retrievedUser);
        assertEquals("Surname", retrievedUser.getSurname());
        assertEquals("Name", retrievedUser.getName());
        assertEquals("PhoneNumber", retrievedUser.getPhoneNumber());
        assertEquals("Email", retrievedUser.getEmail());

        retrievedUser.setSurname("Updated Surname");
        retrievedUser.setName("Updated Name");
        assertTrue(dataProviderCsv.updatePersInf(retrievedUser, sourceCsvPathPersInf));

        PersInf updatedUser = dataProviderCsv.readPersInf(retrievedUser, retrievedUser.getId(), sourceCsvPathPersInf);
        assertNotNull(updatedUser);
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("Updated Surname", updatedUser.getSurname());

        assertTrue(dataProviderCsv.deletePersInf(persInf.getId(), sourceCsvPathPersInf));
    }

    @Test
    void testCRUDMethodsWithPersInfNegativeCSV() throws IOException, CsvException {

        //CreatePersInfWithNull
        PersInf persInf = null;
        assertFalse(dataProviderCsv.createPersInf(persInf, sourceCsvPathPersInf ));

        //ReadPersInfWithNull
        String invalidId = "666";
        CsvException exception = assertThrows(CsvException.class, () -> {
            dataProviderCsv.readPersInf(persInf, invalidId, sourceCsvPathPersInf);
        });
        assertEquals("PersInf object must not be null", exception.getMessage());

        //UpdatePersInfWithNull
        CsvException exceptionNew = assertThrows(CsvException.class, () -> {
            dataProviderCsv.updatePersInf(persInf, sourceCsvPathPersInf);
        });
        assertEquals("PersInf object must not be null", exceptionNew.getMessage());

        //DeletePersInfWithNullId
        String id = null;
        boolean res = dataProviderCsv.deletePersInf(id, sourceCsvPathPersInf);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithProfInfPositiveCSV() throws IOException, CsvException {
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);

        assertTrue(dataProviderCsv.createPersInf(persInf, sourceCsvPathPersInf));
        assertTrue(dataProviderCsv.createProfInf(profInf, persInf, sourceCsvPathProfInf));

        ProfInf retrievedUser = dataProviderCsv.readProfInf(profInf, persInf.getId(), sourceCsvPathProfInf);
        assertNotNull(retrievedUser);
        assertEquals("Programming", retrievedUser.getSkillName());
        assertEquals("Programming in Java", retrievedUser.getSkillDescription());
        assertEquals(2500.00, retrievedUser.getCost());
        assertEquals("Java backend developer", retrievedUser.getPersDescription());
        assertEquals(5.5, retrievedUser.getExp());
        assertEquals(4.0, retrievedUser.getRating());

        retrievedUser.setSkillName("UpdatedSkill");
        assertTrue(dataProviderCsv.updateProfInf(retrievedUser, sourceCsvPathProfInf));

        assertTrue(dataProviderCsv.deletePersInf(persInf.getId(), sourceCsvPathPersInf));
        assertTrue(dataProviderCsv.deleteProfInf(profInf.getPersId(), sourceCsvPathProfInf));
    }

    @Test
    void testCRUDMethodsWithProfInfNegativeCSV() throws IOException, CsvException {

        //CreateProfInfWithNull
        PersInf persInf = null;
        ProfInf profInf = null;
        assertFalse(dataProviderCsv.createProfInf(profInf, persInf, sourceCsvPathProfInf));

        //ReadProfInfWithNonExistingId
        String invalidId = "666";
        CsvException exception = assertThrows(CsvException.class, () -> {
            dataProviderCsv.readProfInf(profInf, invalidId, sourceCsvPathProfInf);
        });
        assertEquals("ProfInf object must not be null", exception.getMessage());

        //UpdateProfInfWithNull
        CsvException exceptionNew = assertThrows(CsvException.class, () -> {
            dataProviderCsv.updateProfInf(profInf, sourceCsvPathProfInf);
        });
        assertEquals("ProfInf object must not be null", exceptionNew.getMessage());

        //DeleteProfInfWithNullId
        String id = null;
        boolean res = dataProviderCsv.deleteProfInf(id,sourceCsvPathProfInf);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithSkillExchangePositiveCSV() throws IOException, CsvException {
        PersInf persInfRequesting = new PersInf("Bober","Curwa","+88005553535", "curwa@mail.ru");
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);
        SkillExchange skillExchange = new SkillExchange(profInf.getSkillName(),persInfRequesting.getId(),profInf.getPersId());

        assertTrue(dataProviderCsv.createPersInf(persInfRequesting, sourceCsvPathPersInf));
        assertTrue(dataProviderCsv.createPersInf(persInf, sourceCsvPathPersInf));
        assertTrue(dataProviderCsv.createProfInf(profInf, persInf, sourceCsvPathProfInf));
        assertTrue(dataProviderCsv.createSkillExchange(skillExchange, sourceCsvPathSkillExchange));

        SkillExchange retrievedSkillExchange = dataProviderCsv.readSkillExchange(skillExchange, sourceCsvPathSkillExchange);
        assertNotNull(retrievedSkillExchange);
        assertNotNull(retrievedSkillExchange);
        assertEquals(persInfRequesting.getId(), retrievedSkillExchange.getUserRequesting());
        assertEquals(profInf.getPersId(), retrievedSkillExchange.getUserOffering());
        assertEquals(profInf.getSkillName(), retrievedSkillExchange.getSkillOffered());

        retrievedSkillExchange.setSkillOffered("UpdatedSkill");
        dataProviderCsv.updateSkillExchange(retrievedSkillExchange, sourceCsvPathSkillExchange);

        SkillExchange updatedSkillExchange = dataProviderCsv.readSkillExchange(skillExchange, sourceCsvPathSkillExchange);
        assertEquals("UpdatedSkill", updatedSkillExchange.getSkillOffered());

        assertTrue(dataProviderCsv.deletePersInf(persInf.getId(), sourceCsvPathPersInf));
        assertTrue(dataProviderCsv.deleteProfInf(profInf.getPersId(), sourceCsvPathProfInf));
        assertTrue(dataProviderCsv.deletePersInf(persInfRequesting.getId(), sourceCsvPathPersInf));
        assertTrue(dataProviderCsv.deleteSkillExchange(skillExchange.getExchangeId(), sourceCsvPathSkillExchange));
    }

    @Test
    void testCRUDMethodsWithSkillExchangeNegativeCSV() throws IOException, CsvException {

        //CreateSkillExchangeWithNull
        SkillExchange skillExchange = null;
        assertFalse(dataProviderCsv.createSkillExchange(skillExchange, sourceCsvPathSkillExchange));

        //ReadSkillExchangeWithNonExistingId
        assertThrows(CsvException.class, () -> {
            dataProviderCsv.readSkillExchange(skillExchange, sourceCsvPathSkillExchange);
        });

        //UpdateSkillExchangeWithNull
        CsvException exceptionNew = assertThrows(CsvException.class, () -> {
            dataProviderCsv.updateSkillExchange(skillExchange, sourceCsvPathSkillExchange);
        });
        assertEquals("SkillExchange object must not be null", exceptionNew.getMessage());

        //DeleteSkillExchangeWithNullId
        String id = null;
        boolean res = dataProviderCsv.deleteSkillExchange(id, sourceCsvPathSkillExchange);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithReviewPositiveCSV() throws IOException, CsvException {
        PersInf persInfReviewer = new PersInf("Bober","Curwa","+88005553535", "curwa@mail.ru");
        PersInf persInfEvaluated = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInfEvaluated.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);
        Review review = new Review(4.5, "Good job!", persInfReviewer.getId(), profInf.getPersId());

        assertTrue(dataProviderCsv.createPersInf(persInfReviewer, sourceCsvPathPersInf));
        assertTrue(dataProviderCsv.createPersInf(persInfEvaluated, sourceCsvPathPersInf));
        assertTrue(dataProviderCsv.createProfInf(profInf, persInfEvaluated, sourceCsvPathProfInf));
        assertTrue(dataProviderCsv.createReview(review, sourceCsvPathReview));

        Review retrievedReview = dataProviderCsv.readReview(review, sourceCsvPathReview);
        assertNotNull(retrievedReview);
        assertEquals(4.5, retrievedReview.getRating());
        assertEquals("Good job!", retrievedReview.getComment());
        assertEquals(persInfReviewer.getId(), retrievedReview.getReviewer());
        assertEquals(profInf.getPersId(), retrievedReview.getUserEvaluated());

        review.setComment("Not God Job!");
        dataProviderCsv.updateReview(review, sourceCsvPathReview);

        Review updatedReview = dataProviderCsv.readReview(review, sourceCsvPathReview);
        assertEquals("Not God Job!", updatedReview.getComment());

        assertTrue(dataProviderCsv.deletePersInf(persInfReviewer.getId(), sourceCsvPathPersInf));
        assertTrue(dataProviderCsv.deleteProfInf(profInf.getPersId(), sourceCsvPathProfInf));
        assertTrue(dataProviderCsv.deletePersInf(persInfEvaluated.getId(), sourceCsvPathPersInf));
        assertTrue(dataProviderCsv.deleteReview(review, sourceCsvPathReview));
    }

    @Test
    void testCRUDMethodsWithReviewNegativeCSV() throws IOException, CsvException {

        //CreateSkillExchangeWithNull
        Review review = null;
        assertFalse(dataProviderCsv.createReview(review, sourceCsvPathReview));

        //ReadReviewWithNonExistingId
        assertThrows(CsvException.class, () -> {
            dataProviderCsv.readReview(review, sourceCsvPathReview);
        });

        //UpdateReviewWithNull
        CsvException exceptionNew = assertThrows(CsvException.class, () -> {
            dataProviderCsv.updateReview(review, sourceCsvPathReview);
        });
        assertEquals("Review object must not be null", exceptionNew.getMessage());

        //DeleteReviewWithNullId
        String id = null;
        boolean res = dataProviderCsv.deleteReview(review, sourceCsvPathPersInf);
        assertFalse(res);
    }

}

