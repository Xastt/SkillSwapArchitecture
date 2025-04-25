package sfedu.xast.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "persinf")
public class PersInf {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "surname")
    @NotEmpty(message = "Enter your surname!")
    @Size(min = 2, max = 60, message = "Your surname should be between 2 and 60!")
    private String surname;

    @Column(name = "name")
    @NotEmpty(message = "Enter your name!")
    @Size(min = 2, max = 60, message = "Your name should be between 2 and 60!")
    private String name;

    @Column(name = "phoneNumber")
    @NotEmpty(message = "Enter your phone number!")
    @Pattern(regexp = "^(\\+7 $\\d{3}$ \\d{3}-\\d{2}-\\d{2}|\\+7\\d{10})$",
            message = "Phone number must be in the format +7 (XXX) XXX-XX-XX or +7XXXXXXXXXX!")
    private String phoneNumber;

    @Column(name = "email")
    @NotEmpty(message = "Enter your email!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Please, enter correct email")
    private String email;

    @OneToMany(mappedBy = "pers", cascade = CascadeType.ALL)
    private List<ProfInf> providedSkills = new ArrayList<>();


    public PersInf(String surname, String name, String phoneNumber, String email) {
        this.id = UUID.randomUUID().toString();
        this.surname = surname;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public PersInf() {}
}
