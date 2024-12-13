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
    void testCRUDMethods() throws SQLException {
        PersInf persInf = new PersInf(1L, "Jackie Chan", "jackie@mail.ru");

        persInfDao.create(persInf);

        PersInf retrievedUser = persInfDao.read(persInf.getId());
        assertNotNull(retrievedUser);
        assertEquals("Test User", retrievedUser.getName());

        retrievedUser.setName("Updated User");
        persInfDao.update(retrievedUser);
        PersInf updatedUser = persInfDao.read(retrievedUser.getId());
        assertEquals("Updated User", updatedUser.getName());

        persInfDao.delete(updatedUser.getId());
        assertNull(persInfDao.read(updatedUser.getId()));
    }


}