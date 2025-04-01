package sfedu.xast.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sfedu.xast.utils.HibernateUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class HibernateDataProviderTest {
    private static HibernateDataProvider dataProvider;

    @BeforeAll
    static void setUp() {
        dataProvider = new HibernateDataProvider(HibernateUtil.getSessionFactory());
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @Test
    void testGetTableNames() {
        List<String> tables = dataProvider.getTableNames();
        assertNotNull(tables);
        assertFalse(tables.isEmpty());
        log.info("Названия таблиц: {}",tables.toString());
    }

    @Test
    void testGetDatabaseSize() {
        String size = dataProvider.getDatabaseSize();
        assertNotNull(size);
        log.info("Размер базы данных: {}", size);
    }

    @Test
    void testGetUsers() {
        List<String> users = dataProvider.getUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
        log.info("Пользователи: {}",users.toString());
    }

    @Test
    void testGetColumnTypes() {
        List<String> columnTypes = dataProvider.getColumnTypes("persinf");
        assertNotNull(columnTypes);
        assertFalse(columnTypes.isEmpty());
        log.info("Типы данны: {}",columnTypes.toString());
    }
}
