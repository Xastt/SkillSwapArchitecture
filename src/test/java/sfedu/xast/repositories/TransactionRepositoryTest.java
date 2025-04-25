package sfedu.xast.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import sfedu.xast.models.SkillExchange;
import sfedu.xast.models.Transaction;
import sfedu.xast.utils.Status;
import sfedu.xast.utils.HibernateUtil;

import java.util.Date;

class TransactionRepositoryTest {

    private static TransactionRepository transactionRepository;
    private static SkillExchangeRepository skillExchangeRepository;
    private static SessionFactory testSessionFactory;

    @BeforeAll
    static void setUp() {
        System.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        System.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        testSessionFactory = HibernateUtil.getSessionFactory();
        transactionRepository = new TransactionRepository(Transaction.class);
        skillExchangeRepository = new SkillExchangeRepository(SkillExchange.class);
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @AfterEach
    void clearDatabase() {
        try (Session session = testSessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM Transaction ").executeUpdate();
            session.createQuery("DELETE FROM SkillExchange").executeUpdate();
            session.getTransaction().commit();
        }
    }

    private SkillExchange createTestSkillExchange() {
        SkillExchange exchange = new SkillExchange("Java Programming", "user1", "user2");
        return skillExchangeRepository.save(exchange);
    }

    private Transaction createTestTransaction() {
        SkillExchange exchange = createTestSkillExchange();
        return new Transaction(Status.IN_PROCESS, exchange);
    }

    @Test
    void testSaveAndFindById() {
        Transaction transaction = createTestTransaction();

        Transaction savedTransaction = transactionRepository.save(transaction);
        assertNotNull(savedTransaction.getTransactionId());
        assertNotNull(savedTransaction.getDate());

        Transaction foundTransaction = transactionRepository.findById(savedTransaction.getTransactionId());
        assertNotNull(foundTransaction);
        assertEquals(Status.IN_PROCESS, foundTransaction.getStatus());
        assertNotNull(foundTransaction.getChangeId());
    }

    @Test
    void testUpdate() {
        Transaction transaction = transactionRepository.save(createTestTransaction());

        transaction.setStatus(Status.COMPLETED);
        Transaction updatedTransaction = transactionRepository.update(transaction);

        assertEquals(Status.COMPLETED, updatedTransaction.getStatus());
        assertEquals(transaction.getTransactionId(), updatedTransaction.getTransactionId());
    }

    @Test
    void testDelete() {
        Transaction transaction = transactionRepository.save(createTestTransaction());

        transactionRepository.delete(transaction.getTransactionId());

        Transaction deletedTransaction = transactionRepository.findById(transaction.getTransactionId());
        assertNull(deletedTransaction);
    }

    @Test
    void testTransactionDateAutoSet() {
        SkillExchange exchange = createTestSkillExchange();
        Transaction transaction = new Transaction(Status.IN_PROCESS, exchange);

        assertNull(transaction.getDate());
        Transaction savedTransaction = transactionRepository.save(transaction);
        assertNotNull(savedTransaction.getDate());

        long diff = Math.abs(savedTransaction.getDate().getTime() - new Date().getTime());
        assertTrue(diff < 5000, "Date should be set to current time");
    }

    @Test
    void testTransactionWithSkillExchange() {
        SkillExchange exchange = createTestSkillExchange();
        Transaction transaction = new Transaction(Status.IN_PROCESS, exchange);

        Transaction savedTransaction = transactionRepository.save(transaction);
        Transaction foundTransaction = transactionRepository.findById(savedTransaction.getTransactionId());

        assertNotNull(foundTransaction.getChangeId());
        assertEquals("Java Programming", foundTransaction.getChangeId().getSkillOffered());
    }
}