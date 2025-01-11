package sfedu.xast.models;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
public class PersInf {
    private String id;
    private String surname;
    private String name;
    private String phoneNumber;
    private String email;

    public PersInf(String surname, String name, String phoneNumber, String email) {
        this.id = UUID.randomUUID().toString();
        this.surname = surname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
