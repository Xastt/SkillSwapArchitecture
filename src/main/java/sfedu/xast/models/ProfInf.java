package sfedu.xast.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfInf {

    private PersInf persId;
    private String skillName;
    private String skillDescription;
    private Double cost;
    private String persDescription;
    private int exp;
    private int rating;

    public ProfInf(String skillName, String skillDescription, Double cost, String persDescription, int exp, int rating) {
        this.skillName = skillName;
        this.skillDescription = skillDescription;
        this.cost = cost;
        this.persDescription = persDescription;
        this.exp = exp;
        this.rating = rating;
    }

}
