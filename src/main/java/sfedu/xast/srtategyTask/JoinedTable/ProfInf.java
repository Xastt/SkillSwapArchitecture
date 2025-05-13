package sfedu.xast.srtategyTask.JoinedTable;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ProfInfJoined")
@PrimaryKeyJoinColumn(name = "person_id")
public class ProfInf extends Person {

   private String skillName;
   private String skillDescription;
   private Double cost;
}
