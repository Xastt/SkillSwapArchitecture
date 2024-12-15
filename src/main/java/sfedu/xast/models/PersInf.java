package sfedu.xast.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersInf {
    //CHANGE TYPE OF ID
    private Long id;
    private String surname;
    private String name;
    private String phoneNumber;
    private String email;

    public PersInf(Long id, String surname, String name, String phoneNumber, String email) {
        this.id = id;
        this.surname = name;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
