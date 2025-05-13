package sfedu.xast.srtategyTask.TablePerClass;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import sfedu.xast.models.ProfInf;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "PersInfTablePerClass")
public class PersInf extends Person {

}

