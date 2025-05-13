package sfedu.xast.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import sfedu.xast.models.PersInf;
import sfedu.xast.utils.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

class PersInfRepositoryTest {

    private static PersInfRepository repository;
    private static SessionFactory testSessionFactory;

    @BeforeAll
    static void setUp() {
        System.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        System.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        testSessionFactory = HibernateUtil.getSessionFactory();
        repository = new PersInfRepository(PersInf.class);
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @AfterEach
    void clearDatabase() {
        try (Session session = testSessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM PersInf").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testSaveAndFindById() {
        PersInf person = new PersInf("Doe", "John", "+71234567890", "john.doe@example.com");

        PersInf savedPerson = repository.save(person);
        assertNotNull(savedPerson.getId());

        PersInf foundPerson = repository.findById(savedPerson.getId());
        assertNotNull(foundPerson);
        assertEquals("Doe", foundPerson.getSurname());
        assertEquals("John", foundPerson.getName());
    }

    @Test
    void testUpdate() {
        PersInf person = new PersInf("Doe", "John", "+71234567890", "john.doe@example.com");
        PersInf savedPerson = repository.save(person);

        savedPerson.setName("Jane");
        PersInf updatedPerson = repository.update(savedPerson);

        assertEquals("Jane", updatedPerson.getName());
        assertEquals(savedPerson.getId(), updatedPerson.getId());
    }

    @Test
    void testDelete() {
        PersInf person = new PersInf("Doe", "John", "+71234567890", "john.doe@example.com");
        PersInf savedPerson = repository.save(person);

        repository.delete(savedPerson.getId());

        PersInf deletedPerson = repository.findById(savedPerson.getId());
        assertNull(deletedPerson);
    }

    @Test
    void testFindByEmail() {
        String email = "unique.email@example.com";
        PersInf person = new PersInf("Smith", "Alice", "+79876543210", email);
        repository.save(person);

        PersInf foundPerson = repository.findByEmail(email);
        assertNotNull(foundPerson);
        assertEquals("Smith", foundPerson.getSurname());
    }

}