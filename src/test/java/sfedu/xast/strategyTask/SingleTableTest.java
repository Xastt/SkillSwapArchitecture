package sfedu.xast.strategyTask;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sfedu.xast.srtategyTask.SingleTable.PersInf;
import sfedu.xast.srtategyTask.SingleTable.ProfInf;
import sfedu.xast.srtategyTask.SingleTable.repoImpl.PersInfRepoImpl;
import sfedu.xast.srtategyTask.SingleTable.repoImpl.ProfInfRepoImpl;
import sfedu.xast.utils.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

public class SingleTableTest {

    private static PersInfRepoImpl persInfRepo;
    private static ProfInfRepoImpl profInfRepo;
    private static SessionFactory testSessionFactory;

    @BeforeAll
    static void setUp() {
        System.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        System.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        testSessionFactory = HibernateUtil.getSessionFactory();
        persInfRepo = new PersInfRepoImpl(testSessionFactory);
        profInfRepo = new ProfInfRepoImpl(testSessionFactory);
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @AfterEach
    void clearDatabase() {
        try (Session session = testSessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM PersInf ").executeUpdate();
            session.createQuery("DELETE FROM ProfInf ").executeUpdate();
            session.getTransaction().commit();
        }
    }

    private PersInf createPersInf() {
        PersInf persInf = new PersInf();
        persInf.setName("Jack");
        persInf.setSurname("Daniels");
        persInf.setPhoneNumber("88005553535");
        persInf.setEmail("ddd@mail.ru");
        return persInf;
    }

    private ProfInf createProfInf() {
        ProfInf profInf = new ProfInf();
        profInf.setSkillName("Java");
        profInf.setSkillDescription("Java java java");
        profInf.setCost(3.0);
        return profInf;
    }

    @Test
    void testSaveAndFindPersInf() {
        PersInf persInf = createPersInf();

        persInfRepo.saveEntity(persInf);

        PersInf foundPersInf = persInfRepo.getEntityById(persInf.getId());
        assertNotNull(foundPersInf);
        assertEquals("Jack", foundPersInf.getName());
        assertEquals("Daniels", foundPersInf.getSurname());
    }

    @Test
    void testSaveAndFindWithProfInf() {
        ProfInf profInf = createProfInf();

        profInfRepo.saveEntity(profInf);

        ProfInf found = profInfRepo.getEntityById(profInf.getId());
        assertNotNull(found);
        assertEquals("Java", found.getSkillName());
    }

    @Test
    void testUpdateDebtor() {
        PersInf persInf = createPersInf();
        persInfRepo.saveEntity(persInf);

        persInf.setName("Upd");
        persInfRepo.updateEntity(persInf);

        assertEquals("Upd", persInf.getName());
    }

    @Test
    void testDeleteDebtor() {
        PersInf persInf = createPersInf();
        persInfRepo.saveEntity(persInf);

        persInfRepo.deleteEntity(persInf);

        PersInf deleted = persInfRepo.getEntityById(persInf.getId());
        assertNull(deleted);
    }
}

