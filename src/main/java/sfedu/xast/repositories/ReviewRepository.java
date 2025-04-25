package sfedu.xast.repositories;

import org.hibernate.Session;
import org.hibernate.query.Query;
import sfedu.xast.models.Review;

public class ReviewRepository extends BaseRepo<Review, String> {

    protected ReviewRepository(Class<Review> entityClass) {
        super(entityClass);
    }

    public boolean updateRating(String userEvaluated, Double newRating, Double currentRating) {
        if (userEvaluated == null || newRating == null || currentRating == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        Double finalRating = currentRating == 0.0 ? newRating : (currentRating + newRating) / 2.0;

        try (Session session = getSession()) {
            session.beginTransaction();

            String hql = "UPDATE Review r SET r.rating = :finalRating WHERE r.userEvaluated = :userEvaluated";
            Query<?> query = session.createQuery(hql);
            query.setParameter("finalRating", finalRating);
            query.setParameter("userEvaluated", userEvaluated);

            int affectedRows = query.executeUpdate();
            session.getTransaction().commit();

            return affectedRows > 0;
        } catch (Exception e) {
            System.err.println("Error updating rating: " + e.getMessage());
            return false;
        }
    }

}
