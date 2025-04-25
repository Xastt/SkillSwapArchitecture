package sfedu.xast.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "skillexchange")
public class SkillExchange {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "exchangeId", nullable = false, updatable = false)
    private String exchangeId;

    @Column(name = "skilloffered")
    private String skillOffered;

    @Column(name = "useroffering")
    private String userOffering;

    @Column(name = "userrequesting")
    private String userRequesting;

    public SkillExchange(String skillOffered, String userRequesting, String userOffering) {
        this.exchangeId = UUID.randomUUID().toString();
        this.skillOffered = skillOffered;
        this.userRequesting = userRequesting;
        this.userOffering = userOffering;
    }

    public SkillExchange() {}
}
