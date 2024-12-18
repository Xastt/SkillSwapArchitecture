package sfedu.xast.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Review {
    private String reviewId;
    private Double rating;
    private String comment;
    private String reviewer;
    private String userEvaluated;
    private String skill;

    public Review(Double rating, String comment,
                  String reviewer, String userEvaluated, String skill) {
        this.reviewId = UUID.randomUUID().toString();
        this.rating = rating;
        this.comment = comment;
        this.reviewer = reviewer;
        this.userEvaluated = userEvaluated;
        this.skill = skill;
    }
}
