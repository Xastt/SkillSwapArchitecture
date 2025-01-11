package sfedu.xast.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class PersForApi implements Serializable {
     private Long id;
     private String name;
     private String email;

     public PersForApi(Long id, String name) {
         this.id = id;
         this.name = name;

     }
}
