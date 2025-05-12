package sfedu.xast.srtategyTask.SingleTable;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("PROF_INF")
public class ProfInfST extends PersonST{

    @ManyToOne
    @JoinColumn(name = "pers_id", nullable = false)
    private PersInfST pers;

    @Column(name = "skillname")
    private String skillName;

    @Column(name = "skilldescription")
    @NotEmpty(message = "Enter your SkillDescription!")
    @Size(max = 1000, message = "Your SkillDescription should be between 0 and 1000!")
    private String skillDescription;

    @Column(name = "cost")
    @Min(value = 0, message = "Your cost should be more than 0!")
    private Double cost;

    @Column(name = "persdescription")
    @NotEmpty(message = "Enter your PersDescription!")
    @Size(max = 1000, message = "Your PersDescription should be between 0 and 1000!")
    private String persDescription;

    @Column(name = "exp")
    private Double exp;

    @Column(name = "rating")
    @Min(value = 0, message = "Your experience should be more than 0!")
    private Double rating;

    public ProfInfST() {
        super();
    }

    public ProfInfST(PersInfST pers, String skillName, String skillDescription,
                     Double cost, String persDescription, Double exp, Double rating) {
        this.pers = pers;
        this.skillName = skillName;
        this.skillDescription = skillDescription;
        this.cost = cost;
        this.persDescription = persDescription;
        this.exp = exp;
        this.rating = rating;
    }

    @PrePersist
    public void prePersist() {
        if (this.rating == null) {
            this.rating = 0.0;
        }
    }

}
