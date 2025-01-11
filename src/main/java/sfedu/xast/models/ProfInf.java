package sfedu.xast.models;

import lombok.*;

@Getter
@Setter
public class ProfInf {

    private String persId;
    private String skillName;
    private String skillDescription;
    private Double cost;
    private String persDescription;
    private Double exp;
    private Double rating;

    public ProfInf(String persId, String skillName, String skillDescription,
                   Double cost, String persDescription, Double exp, Double rating) {
        this.persId = persId;
        this.skillName = skillName;
        this.skillDescription = skillDescription;
        this.cost = cost;
        this.persDescription = persDescription;
        this.exp = exp;
        this.rating = rating;
    }

    public ProfInf(){}
}
