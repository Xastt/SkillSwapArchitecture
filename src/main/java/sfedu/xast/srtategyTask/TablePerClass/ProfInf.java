package sfedu.xast.srtategyTask.TablePerClass;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ProfInfTablePerClass")
public class ProfInf extends Person {

    private String skillName;
    private String skillDescription;
    private Double cost;
}
