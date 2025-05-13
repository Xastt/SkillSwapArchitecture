package sfedu.xast.srtategyTask.MappedSuperclass;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@MappedSuperclass
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surname;
    private String name;
    private String phoneNumber;
    private String email;
}
