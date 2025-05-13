package sfedu.xast.srtategyTask.SingleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "Pers_INF_SINGLE")
@DiscriminatorValue("PERS_INF")
public class PersInf extends Person {

}