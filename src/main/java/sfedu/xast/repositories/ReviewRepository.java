package sfedu.xast.repositories;

import sfedu.xast.models.Review;

public class ReviewRepository extends BaseRepo<Review, String> {

    protected ReviewRepository(Class<Review> entityClass) {
        super(entityClass);
    }

}
