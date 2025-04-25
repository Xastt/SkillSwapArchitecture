package sfedu.xast.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import sfedu.xast.models.Review;
import sfedu.xast.utils.HibernateUtil;

class ReviewRepositoryTest {

    private static ReviewRepository repository;
    private static SessionFactory testSessionFactory;

    @BeforeAll
    static void setUp() {
        System.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        System.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        System.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        testSessionFactory = HibernateUtil.getSessionFactory();
        repository = new ReviewRepository(Review.class);
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @AfterEach
    void clearDatabase() {
        try (Session session = testSessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM Review").executeUpdate();
            session.getTransaction().commit();
        }
    }

    private Review createTestReview() {
        return new Review(4.5, "Great service!", "user1", "user2");
    }

    @Test
    void testSaveAndFindById() {
        Review review = createTestReview();

        Review savedReview = repository.save(review);
        assertNotNull(savedReview.getReviewId());

        Review foundReview = repository.findById(savedReview.getReviewId());
        assertNotNull(foundReview);
        assertEquals(4.5, foundReview.getRating());
        assertEquals("Great service!", foundReview.getComment());
    }

    @Test
    void testUpdate() {
        Review review = repository.save(createTestReview());

        review.setRating(5.0);
        review.setComment("Excellent service!");
        Review updatedReview = repository.update(review);

        assertEquals(5.0, updatedReview.getRating());
        assertEquals("Excellent service!", updatedReview.getComment());
        assertEquals(review.getReviewId(), updatedReview.getReviewId());
    }

    @Test
    void testDelete() {
        Review review = repository.save(createTestReview());

        repository.delete(review.getReviewId());

        Review deletedReview = repository.findById(review.getReviewId());
        assertNull(deletedReview);
    }

    @Test
    void testUpdateRating_FirstRating() {
        Review review = new Review(0.0, "Test comment", "reviewer1", "user3");
        repository.save(review);

        boolean result = repository.updateRating("user3", 4.5, 0.0);

        assertTrue(result);

        Review updatedReview = repository.findById(review.getReviewId());
        assertEquals(4.5, updatedReview.getRating());
    }

    @Test
    void testUpdateRating_UpdateExistingRating() {
        Review review = new Review(3.0, "Test comment", "reviewer1", "user4");
        repository.save(review);

        boolean result = repository.updateRating("user4", 5.0, 3.0);

        assertTrue(result);

        Review updatedReview = repository.findById(review.getReviewId());
        assertEquals(4.0, updatedReview.getRating()); // (3.0 + 5.0) / 2 = 4.0
    }

}