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

        PersInf retrievedUser = dataProviderPSQL.readPersInf(persInf.getId());
        assertNotNull(retrievedUser);
        assertEquals("Jackie", retrievedUser.getSurname());
        assertEquals("Chan", retrievedUser.getName());
        assertEquals("+79180540546", retrievedUser.getEmail());
        assertEquals("jackie@mail.ru", retrievedUser.getEmail());

        retrievedUser.setSurname("Updated");
        retrievedUser.setName("User");
        dataProviderPSQL.updatePersInf(retrievedUser);

        PersInf updatedUser = dataProviderPSQL.readPersInf(retrievedUser.getId());
        assertEquals("Updated", updatedUser.getSurname());
        assertEquals("User", updatedUser.getName());

        dataProviderPSQL.deletePersInf(persInf.getId());
        assertNull(dataProviderPSQL.readPersInf(persInf.getId()));
    }

    @Test
    void testCRUDMethodsWithProfInf() throws SQLException {
        PersInf persInf = new PersInf("Jackie","Chan","+79180540546", "jackie@mail.ru");
        ProfInf profInf = new ProfInf(persInf.getId(), "Programming","Programming in Java", 2500.00,
                "Java backend developer", 5.5, 4.0);

        dataProviderPSQL.createProfInf(profInf, persInf);

        ProfInf retrievedUser = dataProviderPSQL.readProfInf(profInf.getPersId());
        assertNotNull(retrievedUser);
        assertEquals("Programming", retrievedUser.getSkillName());
        assertEquals("Programming in Java", retrievedUser.getSkillDescription());
        assertEquals(2500.00, retrievedUser.getCost());
        assertEquals("Java backend developer", retrievedUser.getPersDescription());
        assertEquals(5.5, retrievedUser.getExp());
        assertEquals(4.0, retrievedUser.getRating());

        retrievedUser.setSkillName("UpdatedSkill");
        dataProviderPSQL.updateProfInf(retrievedUser);

        ProfInf updatedUser = dataProviderPSQL.readProfInf(retrievedUser.getPersId());
        assertEquals("UpdatedSkill", updatedUser.getSkillName());

        dataProviderPSQL.deleteProfInf(profInf.getPersId());
        assertNull(dataProviderPSQL.readProfInf(profInf.getPersId()));
    }

}

