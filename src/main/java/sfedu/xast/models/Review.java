package sfedu.xast.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "review")
public class Review {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "reviewId", nullable = false, updatable = false)
    private String reviewId;

    @Column(name = "rating")
    @Min(value = 0, message = "Your rating should be between 1 and 5!")
    @Max(value = 5, message = "Your rating should be between 1 and 5!")
    private Double rating;

    @Column(name = "comment")
    @NotEmpty(message = "Enter your comment!")
    private String comment;

    @Column(name = "reviewer")
    private String reviewer;

    @Column(name = "userevaluated")
    private String userEvaluated;

    public Review(Double rating, String comment,
                  String reviewer, String userEvaluated) {
        this.rating = rating;
        this.comment = comment;
        this.reviewer = reviewer;
        this.userEvaluated = userEvaluated;
    }

    public Review() {}
}
