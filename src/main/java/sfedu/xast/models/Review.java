package sfedu.xast.models;

import lombok.Getter;
import lombok.Setter;

//ID
@Getter
@Setter
public class Review {
    private int reviewId;
    private int rating;
    private String comment;
    private PersInf reviewer;
    private PersInf userEvaluated;
    private PersInf skill;

    public Review(int reviewId, int rating, String comment,
                  PersInf reviewer, PersInf userEvaluated, PersInf skill) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.comment = comment;
        this.reviewer = reviewer;
        this.userEvaluated = userEvaluated;
        this.skill = skill;
    }
}
