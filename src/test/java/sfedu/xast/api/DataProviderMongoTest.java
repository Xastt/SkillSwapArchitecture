package sfedu.xast.api;

import com.mongodb.MongoException;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfedu.xast.models.HistoryContent;
import sfedu.xast.models.PersInf;
import sfedu.xast.models.ProfInf;

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
        //CreatePersInfWithNull
        PersInf persInf = null;
        ProfInf profInf = null;
        assertFalse(dataProviderMongo.createProfInf(profInf, persInf));

        //ReadPersInfWithNull
        String invalidId = "666";
        MongoException exception = assertThrows(MongoException.class, () -> {
            dataProviderMongo.readProfInf(profInf, invalidId);
        });
        assertEquals("ProfInf object must not be null", exception.getMessage());

        //UpdatePersInfWithNull
        assertFalse(dataProviderMongo.updateProfInf(profInf));

        //DeletePersInfWithNullId
        String id = null;
        boolean res = dataProviderMongo.deleteProfInf(id);
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
