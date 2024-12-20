package sfedu.xast.api;

import sfedu.xast.models.*;
import java.io.*;
import java.sql.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderPSQLTest {
    private DataProviderPSQL dataProviderPSQL;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        Connection connection = DataProviderPSQL.getConnection();
        dataProviderPSQL = new DataProviderPSQL();
    }

    @Test
    public void shouldGetJdbcConnection() throws SQLException, IOException {
        try(Connection connection = DataProviderPSQL.getConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }

    @Test
    void testCRUDMethodsWithPersInf() throws SQLException {
        PersInf persInf = new PersInf("Jackie","Chan","+79180540546", "jackie@mail.ru");

        dataProviderPSQL.createPersInf(persInf);

        PersInf retrievedUser = dataProviderPSQL.readPersInf(persInf, persInf.getId());
        assertNotNull(retrievedUser);
        assertEquals("Jackie", retrievedUser.getSurname());
        assertEquals("Chan", retrievedUser.getName());
        assertEquals("+79180540546", retrievedUser.getPhoneNumber());
        assertEquals("jackie@mail.ru", retrievedUser.getEmail());

        retrievedUser.setSurname("Updated");
        retrievedUser.setName("User");
        dataProviderPSQL.updatePersInf(retrievedUser);


        PersInf updatedUser = dataProviderPSQL.readPersInf(retrievedUser, retrievedUser.getId());
        assertEquals("Updated", updatedUser.getSurname());
        assertEquals("User", updatedUser.getName());

        dataProviderPSQL.deletePersInf(persInf.getId());
    }

    @Test
    void testCRUDMethodsWithProfInf() throws SQLException {
        PersInf persInf = new PersInf("Jackie","Chan","+79180540546", "jackie@mail.ru");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);

        dataProviderPSQL.createPersInf(persInf);
        dataProviderPSQL.createProfInf(profInf, persInf);

        ProfInf retrievedUser = dataProviderPSQL.readProfInf(profInf, persInf.getId());
        assertNotNull(retrievedUser);
        assertEquals("Programming", retrievedUser.getSkillName());
        assertEquals("Programming in Java", retrievedUser.getSkillDescription());
        assertEquals(2500.00, retrievedUser.getCost());
        assertEquals("Java backend developer", retrievedUser.getPersDescription());
        assertEquals(5.5, retrievedUser.getExp());
        assertEquals(4.0, retrievedUser.getRating());

        retrievedUser.setSkillName("UpdatedSkill");
        dataProviderPSQL.updateProfInf(retrievedUser);

        ProfInf updatedUser = dataProviderPSQL.readProfInf(profInf, retrievedUser.getPersId());
        assertEquals("UpdatedSkill", updatedUser.getSkillName());

        dataProviderPSQL.deleteProfInf(profInf.getPersId());
        dataProviderPSQL.deletePersInf(persInf.getId());
    }

    @Test
    void testCRUDMethodsWithSkillExchange() throws SQLException {
        PersInf persInfRequesting = new PersInf("Bober","Curwa","+88005553535", "curwa@mail.ru");
        PersInf persInf = new PersInf("Jackie","Chan","+87005553636", "jackie@mail.ru");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);
        SkillExchange skillExchange = new SkillExchange(profInf.getSkillName(),persInfRequesting.getId(),profInf.getPersId());

        dataProviderPSQL.createPersInf(persInf);
        dataProviderPSQL.createPersInf(persInfRequesting);
        dataProviderPSQL.createProfInf(profInf, persInf);
        dataProviderPSQL.createSkillExchange(skillExchange);

        SkillExchange retrievedSkillExchange = dataProviderPSQL.readSkillExchange(skillExchange);
        assertNotNull(retrievedSkillExchange);
        assertEquals(persInfRequesting.getId(), retrievedSkillExchange.getUserRequesting());
        assertEquals(profInf.getPersId(), retrievedSkillExchange.getUserOffering());
        assertEquals(profInf.getSkillName(), retrievedSkillExchange.getSkillOffered());

        retrievedSkillExchange.setSkillOffered("UpdatedSkill");
        dataProviderPSQL.updateSkillExchange(retrievedSkillExchange);

        SkillExchange updatedSkillExchange = dataProviderPSQL.readSkillExchange(retrievedSkillExchange);
        assertEquals("UpdatedSkill", updatedSkillExchange.getSkillOffered());

        dataProviderPSQL.deleteProfInf(profInf.getPersId());
        dataProviderPSQL.deletePersInf(persInf.getId());
        dataProviderPSQL.deletePersInf(persInfRequesting.getId());
        dataProviderPSQL.deleteSkillExchange(skillExchange.getExchangeId());
    }

}

