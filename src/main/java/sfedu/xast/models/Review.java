package sfedu.xast.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class Review {
    private String reviewId;
    private Double rating;
    private String comment;
    private String reviewer;
    private String userEvaluated;

    public Review(Double rating, String comment,
                  String reviewer, String userEvaluated) {
        this.reviewId = UUID.randomUUID().toString();
        this.rating = rating;
        this.comment = comment;
        this.reviewer = reviewer;
        this.userEvaluated = userEvaluated;
    }
}
