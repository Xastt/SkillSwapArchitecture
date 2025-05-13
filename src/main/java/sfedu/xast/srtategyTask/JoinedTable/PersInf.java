package sfedu.xast.srtategyTask.JoinedTable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "PersInfJoined")
@PrimaryKeyJoinColumn(name = "person_id")
public class PersInf extends Person {

}
