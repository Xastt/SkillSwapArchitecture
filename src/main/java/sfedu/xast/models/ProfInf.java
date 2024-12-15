package sfedu.xast.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfInf extends PersInf{

    private String skillName;
    private String skillDescription;
    private Double cost;
    private String persDescription;
    private int exp;
    private int rating;

    public ProfInf(Long id, String surname, String name, String phoneNumber, String email,
                   String skillName, String skillDescription, Double cost, String persDescription, int exp, int rating) {
        super(id, surname, name, phoneNumber, email);
        this.skillName = skillName;
        this.skillDescription = skillDescription;
        this.cost = cost;
        this.persDescription = persDescription;
        this.exp = exp;
        this.rating = rating;
    }
}
