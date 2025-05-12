package sfedu.xast.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import sfedu.xast.models.PersInf;
import sfedu.xast.models.ProfInf;
import sfedu.xast.utils.HibernateUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfInfRepositoryTest {

    private static ProfInfRepository profInfRepository;
    private static PersInfRepository persInfRepository;
    private static SessionFactory testSessionFactory;

    @BeforeAll
    static void setUp() {
        System.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        System.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        testSessionFactory = HibernateUtil.getSessionFactory();
        profInfRepository = new ProfInfRepository(ProfInf.class);
        persInfRepository = new PersInfRepository(PersInf.class);
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @AfterEach
    void clearDatabase() {
        try (Session session = testSessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM ProfInfMS").executeUpdate();
            session.createQuery("DELETE FROM PersInfTPC").executeUpdate();
            session.getTransaction().commit();
        }
    }

    private PersInf createTestPerson() {
        PersInf person = new PersInf("Doe", "John", "+71234567890", "john.doe@example.com");
        return persInfRepository.save(person);
    }

    private ProfInf createTestProfInf(PersInf person) {
        ProfInf profInf = new ProfInf(
                person,
                "Java Development",
                "Backend development with Spring Boot",
                50.0,
                "Experienced Java developer",
                5.0,
                4.8
        );
        return profInfRepository.save(profInf);
    }

    @Test
    void testSaveAndFindById() {
        PersInf person = createTestPerson();
        ProfInf profInf = createTestProfInf(person);

        ProfInf foundProfInf = profInfRepository.findById(profInf.getId());
        assertNotNull(foundProfInf);
        assertEquals("Java Development", foundProfInf.getSkillName());
        assertEquals(person.getId(), foundProfInf.getPers().getId());
    }

    @Test
    void testUpdate() {
        PersInf person = createTestPerson();
        ProfInf profInf = createTestProfInf(person);

        profInf.setSkillName("Spring Boot Development");
        ProfInf updatedProfInf = profInfRepository.update(profInf);

        assertEquals("Spring Boot Development", updatedProfInf.getSkillName());
        assertEquals(profInf.getId(), updatedProfInf.getId());
    }

    @Test
    void testDelete() {
        PersInf person = createTestPerson();
        ProfInf profInf = createTestProfInf(person);

        profInfRepository.delete(profInf.getId());

        ProfInf deletedProfInf = profInfRepository.findById(profInf.getId());
        assertNull(deletedProfInf);
    }

    @Test
    void testFindByPersId() {
        PersInf person = createTestPerson();
        ProfInf profInf1 = new ProfInf(
                person,
                "Spring Framework",
                "Spring Core, MVC, Security",
                60.0,
                "Spring expert",
                7.0,
                4.9
        );
        profInfRepository.save(profInf1);

        List<ProfInf> foundProfInfs = profInfRepository.findByPersId(person.getId());
        assertEquals(1, foundProfInfs.size());
    }

    @Test
    void testFindBySkillName() {
        PersInf person = createTestPerson();
        ProfInf profInf1 = new ProfInf(
                person,
                "Spring Framework",
                "Spring Core, MVC, Security",
                60.0,
                "Spring expert",
                7.0,
                4.9
        );
        profInfRepository.save(profInf1);

        List<ProfInf> springSkills = profInfRepository.findBySkillName("Spring");
        assertEquals(1, springSkills.size());
    }
}