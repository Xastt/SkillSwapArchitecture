package sfedu.xast.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "test_entity")
public class TestEntity  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(name = "is_check", nullable = false)
    private Boolean is_check;

    @Embedded
    private Address address;

    public TestEntity(){}

    public TestEntity(String name, String description, Boolean is_check, Address address) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.dateCreated = new Date();
        this.is_check = is_check;
        this.address = address;
    }

}