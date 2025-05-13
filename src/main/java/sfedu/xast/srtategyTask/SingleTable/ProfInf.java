package sfedu.xast.srtategyTask.SingleTable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("PROF_INF")
@Entity(name = "Prof_INF_SINGLE")
public class ProfInf extends Person {

    private String skillName;
    private String skillDescription;
    private Double cost;

}
