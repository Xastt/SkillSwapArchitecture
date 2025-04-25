package sfedu.xast.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import sfedu.xast.models.SkillExchange;
import sfedu.xast.utils.HibernateUtil;

class SkillExchangeRepositoryTest {

    private static SkillExchangeRepository repository;
    private static SessionFactory testSessionFactory;

    @BeforeAll
    static void setUp() {
        System.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        System.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        testSessionFactory = HibernateUtil.getSessionFactory();
        repository = new SkillExchangeRepository(SkillExchange.class);
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @AfterEach
    void clearDatabase() {
        try (Session session = testSessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM SkillExchange").executeUpdate();
            session.getTransaction().commit();
        }
    }

    private SkillExchange createTestExchange() {
        return new SkillExchange("Java Programming", "user123", "user456");
    }

    @Test
    void testSaveAndFindById() {
        SkillExchange exchange = createTestExchange();

        SkillExchange savedExchange = repository.save(exchange);
        assertNotNull(savedExchange.getExchangeId());

        SkillExchange foundExchange = repository.findById(savedExchange.getExchangeId());
        assertNotNull(foundExchange);
        assertEquals("Java Programming", foundExchange.getSkillOffered());
        assertEquals("user123", foundExchange.getUserRequesting());
        assertEquals("user456", foundExchange.getUserOffering());
    }

    @Test
    void testUpdate() {
        SkillExchange exchange = repository.save(createTestExchange());

        exchange.setSkillOffered("Spring Framework");
        exchange.setUserOffering("user789");
        SkillExchange updatedExchange = repository.update(exchange);

        assertEquals("Spring Framework", updatedExchange.getSkillOffered());
        assertEquals("user789", updatedExchange.getUserOffering());
        assertEquals(exchange.getExchangeId(), updatedExchange.getExchangeId());
    }

    @Test
    void testDelete() {
        SkillExchange exchange = repository.save(createTestExchange());

        repository.delete(exchange.getExchangeId());

        SkillExchange deletedExchange = repository.findById(exchange.getExchangeId());
        assertNull(deletedExchange);
    }

}