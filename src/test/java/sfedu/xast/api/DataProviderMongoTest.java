package sfedu.xast.api;

import com.mongodb.MongoException;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfedu.xast.models.*;
import sfedu.xast.utils.Status;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DataProviderMongoTest {

    private DataProviderMongo dataProviderMongo;

    @BeforeEach
    void setUp() {
        dataProviderMongo = new DataProviderMongo("test");
    }

    @Test
    void testCRUDMethodsWithPersInfPositiveMongo() throws IOException, CsvException {
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");

        assertTrue(dataProviderMongo.createPersInf(persInf));

        PersInf retrievedUser = dataProviderMongo.readPersInf(persInf, persInf.getId());
        assertNotNull(retrievedUser);
        assertEquals("Surname", retrievedUser.getSurname());
        assertEquals("Name", retrievedUser.getName());
        assertEquals("PhoneNumber", retrievedUser.getPhoneNumber());
        assertEquals("Email", retrievedUser.getEmail());

        retrievedUser.setSurname("Updated Surname");
        retrievedUser.setName("Updated Name");
        assertTrue(dataProviderMongo.updatePersInf(retrievedUser));

        PersInf updatedUser = dataProviderMongo.readPersInf(retrievedUser, retrievedUser.getId());
        assertNotNull(updatedUser);
        assertEquals("Updated Name", updatedUser.getName());
        assertEquals("Updated Surname", updatedUser.getSurname());

        assertTrue(dataProviderMongo.deletePersInf(persInf.getId()));
    }

    @Test
    void testCRUDMethodsWithPersInfNegativeMongo() {
        //CreatePersInfWithNull
        PersInf persInf = null;
        assertFalse(dataProviderMongo.createPersInf(persInf));

        //ReadPersInfWithNull
        String invalidId = "666";
        MongoException exception = assertThrows(MongoException.class, () -> {
            dataProviderMongo.readPersInf(persInf, invalidId);
        });
        assertEquals("PersInf object must not be null", exception.getMessage());

        //UpdatePersInfWithNull
       assertFalse(dataProviderMongo.updatePersInf(persInf));

        //DeletePersInfWithNullId
        String id = null;
        boolean res = dataProviderMongo.deletePersInf(id);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithProfInfPositiveMongo() throws IOException, CsvException {
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);

        assertTrue(dataProviderMongo.createPersInf(persInf));
        assertTrue(dataProviderMongo.createProfInf(profInf, persInf));

        ProfInf retrievedUser = dataProviderMongo.readProfInf(profInf, persInf.getId());
        assertNotNull(retrievedUser);
        assertEquals("Programming", retrievedUser.getSkillName());
        assertEquals("Programming in Java", retrievedUser.getSkillDescription());
        assertEquals(2500.00, retrievedUser.getCost());
        assertEquals("Java backend developer", retrievedUser.getPersDescription());
        assertEquals(5.5, retrievedUser.getExp());
        assertEquals(4.0, retrievedUser.getRating());

        retrievedUser.setSkillName("UpdatedSkill");
        assertTrue(dataProviderMongo.updateProfInf(retrievedUser));

        assertTrue(dataProviderMongo.deletePersInf(persInf.getId()));
        assertTrue(dataProviderMongo.deleteProfInf(profInf.getPersId()));
    }

    @Test
    void testCRUDMethodsWithProfInfNegativeMongo() {
        //CreateWithNull
        PersInf persInf = null;
        ProfInf profInf = null;
        assertFalse(dataProviderMongo.createProfInf(profInf, persInf));

        //ReadWithNull
        String invalidId = "666";
        MongoException exception = assertThrows(MongoException.class, () -> {
            dataProviderMongo.readProfInf(profInf, invalidId);
        });
        assertEquals("ProfInf object must not be null", exception.getMessage());

        //UpdateWithNull
        assertFalse(dataProviderMongo.updateProfInf(profInf));

        //DeleteWithNullId
        String id = null;
        boolean res = dataProviderMongo.deleteProfInf(id);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithSkillExchangePositiveMongo() throws IOException, CsvException {
        PersInf persInfRequesting = new PersInf("Bober","Curwa","+88005553535", "curwa@mail.ru");
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);
        SkillExchange skillExchange = new SkillExchange(profInf.getSkillName(),persInfRequesting.getId(),profInf.getPersId());

        assertTrue(dataProviderMongo.createPersInf(persInfRequesting));
        assertTrue(dataProviderMongo.createPersInf(persInf));
        assertTrue(dataProviderMongo.createProfInf(profInf, persInf));
        assertTrue(dataProviderMongo.createSkillExchange(skillExchange));

        SkillExchange retrievedSkillExchange = dataProviderMongo.readSkillExchange(skillExchange);
        assertNotNull(retrievedSkillExchange);
        assertNotNull(retrievedSkillExchange);
        assertEquals(persInfRequesting.getId(), retrievedSkillExchange.getUserRequesting());
        assertEquals(profInf.getPersId(), retrievedSkillExchange.getUserOffering());
        assertEquals(profInf.getSkillName(), retrievedSkillExchange.getSkillOffered());

        retrievedSkillExchange.setSkillOffered("UpdatedSkill");
        dataProviderMongo.updateSkillExchange(retrievedSkillExchange);

        SkillExchange updatedSkillExchange = dataProviderMongo.readSkillExchange(skillExchange);
        assertEquals("UpdatedSkill", updatedSkillExchange.getSkillOffered());

        assertTrue(dataProviderMongo.deletePersInf(persInf.getId()));
        assertTrue(dataProviderMongo.deleteProfInf(profInf.getPersId()));
        assertTrue(dataProviderMongo.deletePersInf(persInfRequesting.getId()));
        assertTrue(dataProviderMongo.deleteSkillExchange(skillExchange.getExchangeId()));
    }

    @Test
    void testCRUDMethodsWithSkillExchangeNegativeMongo() {
        //CreateWithNull
        SkillExchange skillExchange = null;
        assertFalse(dataProviderMongo.createSkillExchange(skillExchange));

        //ReadWithNonExistingId
        String invalidId = "666";
        MongoException exception = assertThrows(MongoException.class, () -> {
            dataProviderMongo.readSkillExchange(skillExchange);
        });
        assertEquals("SkillExchange object must not be null", exception.getMessage());

        //UpdateWithNull
        assertFalse(dataProviderMongo.updateSkillExchange(skillExchange));

        //DeleteWithNullId
        String id = null;
        boolean res = dataProviderMongo.deleteSkillExchange(id);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithReviewPositiveMongo() throws IOException, CsvException {
        PersInf persInfReviewer = new PersInf("Bober","Curwa","+88005553535", "curwa@mail.ru");
        PersInf persInfEvaluated = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInfEvaluated.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);
        Review review = new Review(4.5, "Good job!", persInfReviewer.getId(), profInf.getPersId());

        assertTrue(dataProviderMongo.createPersInf(persInfReviewer));
        assertTrue(dataProviderMongo.createPersInf(persInfEvaluated));
        assertTrue(dataProviderMongo.createProfInf(profInf, persInfEvaluated));
        assertTrue(dataProviderMongo.createReview(review));

        Review retrievedReview = dataProviderMongo.readReview(review);
        assertNotNull(retrievedReview);
        assertEquals(4.5, retrievedReview.getRating());
        assertEquals("Good job!", retrievedReview.getComment());
        assertEquals(persInfReviewer.getId(), retrievedReview.getReviewer());
        assertEquals(profInf.getPersId(), retrievedReview.getUserEvaluated());

        review.setComment("Not God Job!");
        dataProviderMongo.updateReview(review);

        Review updatedReview = dataProviderMongo.readReview(review);
        assertEquals("Not God Job!", updatedReview.getComment());

        assertTrue(dataProviderMongo.deletePersInf(persInfReviewer.getId()));
        assertTrue(dataProviderMongo.deleteProfInf(profInf.getPersId()));
        assertTrue(dataProviderMongo.deletePersInf(persInfEvaluated.getId()));
        assertTrue(dataProviderMongo.deleteReview(review));
    }

    @Test
    void testCRUDMethodsWithReviewNegativeMongo() {
        //CreateWithNull
        Review review = null;
        assertFalse(dataProviderMongo.createReview(review));

        //ReadWithNonExistingId
        assertThrows(MongoException.class, () -> {
            dataProviderMongo.readReview(review);
        });

        //UpdateWithNull
        assertFalse(dataProviderMongo.updateReview(review));

        //DeleteWithNullId
        String id = null;
        boolean res = dataProviderMongo.deleteReview(review);
        assertFalse(res);
    }

    @Test
    void testCRUDMethodsWithTransactionPositiveMongo() throws IOException, CsvException {
        PersInf persInfRequesting = new PersInf("Bober","Curwa","+88005553535", "curwa@mail.ru");
        PersInf persInf = new PersInf("Surname", "Name", "PhoneNumber", "Email");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);
        SkillExchange skillExchange = new SkillExchange(profInf.getSkillName(),persInfRequesting.getId(),profInf.getPersId());
        Transaction transaction = new Transaction(Status.COMPLETED, skillExchange.getExchangeId());

        assertTrue(dataProviderMongo.createPersInf(persInfRequesting));
        assertTrue(dataProviderMongo.createPersInf(persInf));
        assertTrue(dataProviderMongo.createProfInf(profInf, persInf));
        assertTrue(dataProviderMongo.createSkillExchange(skillExchange));
        assertTrue(dataProviderMongo.createTransaction(transaction));

        Transaction retrievedTransaction = dataProviderMongo.readTransaction(transaction);
        assertNotNull(retrievedTransaction);
        assertEquals(Status.COMPLETED, retrievedTransaction.getStatus());
        assertEquals(skillExchange.getExchangeId(), transaction.getChangeId());

        retrievedTransaction.setStatus(Status.IN_PROCESS);
        dataProviderMongo.updateTransaction(transaction);

        Transaction updatedTransaction = dataProviderMongo.readTransaction(transaction);
        assertEquals(Status.IN_PROCESS, updatedTransaction.getStatus());

        assertTrue(dataProviderMongo.deleteTransaction(transaction));
        assertTrue(dataProviderMongo.deletePersInf(persInf.getId()));
        assertTrue(dataProviderMongo.deleteProfInf(profInf.getPersId()));
        assertTrue(dataProviderMongo.deletePersInf(persInfRequesting.getId()));
        assertTrue(dataProviderMongo.deleteSkillExchange(skillExchange.getExchangeId()));
    }

    @Test
    void testCRUDMethodsWithTransactionNegativeMongo() {
        //CreateWithNull
        Transaction transaction = null;
        assertFalse(dataProviderMongo.createTransaction(transaction));

        //ReadWithNonExistingId
        assertThrows(MongoException.class, () -> {
            dataProviderMongo.readTransaction(transaction);
        });

        //UpdateWithNull
        assertFalse(dataProviderMongo.updateTransaction(transaction));

        //DeleteWithNullId
        String id = null;
        boolean res = dataProviderMongo.deleteTransaction(transaction);
        assertFalse(res);
    }

    @Test
    public void testCRUDWithMongoDB(){
        DataProviderMongo dataProvider = new DataProviderMongo("test");

        HistoryContent content = new HistoryContent();
        content.setId("1");
        content.setClassName("ExampleClass");
        content.setMethodName("exampleMethod");
        content.setObject(Map.of("key1", "value1", "key2", "value2"));
        content.setStatus(HistoryContent.Status.SUCCESS);

        dataProvider.insertHistoryContent(content);

        HistoryContent retrieved = dataProvider.findHistoryContentById("1");
        System.out.println(retrieved);

        content.setStatus(HistoryContent.Status.FAULT);
        dataProvider.updateHistoryContent(content);

        dataProvider.deleteHistoryContent("1");
    }



}
