package sfedu.xast.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sfedu.xast.models.PersInf;
import sfedu.xast.utils.GetDatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PersInfDaoTest {

    private PersInfDao persInfDao;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        Connection connection = GetDatabaseConnection.getConnection();
        persInfDao = new PersInfDao(connection);
    }

    @Test
    public void shouldGetJdbcConnection() throws SQLException, IOException {
        try(Connection connection = GetDatabaseConnection.getConnection()) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }
    }

    @Test
    void testCRUDMethods() throws SQLException {
        PersInf persInf = new PersInf(1L, "Jackie","Chan","+79180540546", "jackie@mail.ru");

        persInfDao.create(persInf);

        PersInf retrievedUser = persInfDao.read(persInf.getId());
        assertNotNull(retrievedUser);
        assertEquals("Jackie", retrievedUser.getSurname());
        assertEquals("Chan", retrievedUser.getName());
        assertEquals("+79180540546", retrievedUser.getEmail());
        assertEquals("jackie@mail.ru", retrievedUser.getEmail());

        retrievedUser.setSurname("Updated User");
        persInfDao.update(retrievedUser);
        PersInf updatedUser = persInfDao.read(retrievedUser.getId());
        assertEquals("Updated User", updatedUser.getSurname());

        persInfDao.delete(updatedUser.getId());
        assertNull(persInfDao.read(updatedUser.getId()));
    }


}