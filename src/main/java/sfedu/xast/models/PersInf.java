package sfedu.xast.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersInf {
    private Long id;
    private String name;
    private String email;

    public PersInf(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
