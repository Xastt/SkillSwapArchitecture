package sfedu.xast.srtategyTask.MappedSuperclass;

import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "PersInfMapped")
public class PersInf extends Person {
}
