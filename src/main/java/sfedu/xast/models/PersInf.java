package sfedu.xast.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class PersInf implements Serializable {
     private Long id;
     private String name;

     public PersInf(Long id, String name) {
         this.id = id;
         this.name = name;
     }
}
