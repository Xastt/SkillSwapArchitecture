package sfedu.xast;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import sfedu.xast.models.PersInf;
import sfedu.xast.models.ProfInf;
import sfedu.xast.repositories.PersInfRepository;
import sfedu.xast.repositories.ProfInfRepository;
import sfedu.xast.utils.HibernateUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

class QueryPerfomanceTest {

    private static PersInfRepository persInfRepository;
    private static ProfInfRepository profInfRepository;
    private static SessionFactory testSessionFactory;

    @BeforeAll
    static void setUp() {
        System.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        System.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        testSessionFactory = HibernateUtil.getSessionFactory();
        persInfRepository = new PersInfRepository(PersInf.class);
        profInfRepository = new ProfInfRepository(ProfInf.class);
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @AfterEach
    void clearDatabase() {
        try (Session session = testSessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM ProfInf").executeUpdate();
            session.createQuery("DELETE FROM PersInf").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testQueryPerformance() {
        // Создаем тестовые данные
        createTestData();

        // Измеряем время выполнения Native SQL запроса
        long nativeStartTime = System.nanoTime();
        List<Object[]> nativeResults = persInfRepository.getUserSkillsSummaryNative();
        long nativeEndTime = System.nanoTime();
        long nativeDuration = TimeUnit.NANOSECONDS.toMicros(nativeEndTime - nativeStartTime);

        // Измеряем время выполнения HQL запроса
        long hqlStartTime = System.nanoTime();
        List<Object[]> hqlResults = persInfRepository.getUserSkillsSummaryHQL();
        long hqlEndTime = System.nanoTime();
        long hqlDuration = TimeUnit.NANOSECONDS.toMicros(hqlEndTime - hqlStartTime);

        // Измеряем время выполнения Criteria API запроса
        long criteriaStartTime = System.nanoTime();
        List<Object[]> criteriaResults = profInfRepository.getUserSkillsSummaryCriteria();
        long criteriaEndTime = System.nanoTime();
        long criteriaDuration = TimeUnit.NANOSECONDS.toMicros(criteriaEndTime - criteriaStartTime);

        // Проверяем результаты
        assertAll(
                () -> assertFalse(nativeResults.isEmpty()),
                () -> assertFalse(hqlResults.isEmpty()),
                () -> assertFalse(criteriaResults.isEmpty())
        );

        System.out.println("Производительность запросов:");
        System.out.printf("Native SQL: %d микросекунд%n", nativeDuration);
        System.out.printf("HQL: %d микросекунд%n", hqlDuration);
        System.out.printf("Criteria API: %d микросекунд%n", criteriaDuration);
        System.out.println();

        System.out.println("Соотношение производительности:");
        System.out.printf("HQL/Native: %.2f%n", (double)hqlDuration/nativeDuration);
        System.out.printf("Criteria/Native: %.2f%n", (double)criteriaDuration/nativeDuration);
    }

    private void createTestData() {

        for (int i = 0; i < 10; i++) {
            PersInf user = new PersInf("Фамилия" + i, "Имя" + i,
                    "+7912" + String.format("%07d", i),
                    "user" + i + "@example.com");
            persInfRepository.save(user);

            for (int j = 0; j < 1; j++) {
                ProfInf skill = new ProfInf(user,
                        "Навык" + j,
                        "Описание навыка " + j,
                        1000.0 + j * 100,
                        "Описание исполнителя " + j,
                        j + 1.0,
                        3.0 + j * 0.5);
                profInfRepository.save(skill);
            }
        }
    }

    private void printResults(String title, List<Object[]> results) {
        System.out.println(title);
        for (Object[] row : results) {
            System.out.printf(
                    "Фамилия: %s, Имя: %s, Навыков: %d, Ср. рейтинг: %.2f%n",
                    row[0], row[1], row[2], row[3]
            );
        }
        System.out.println();
    }
}