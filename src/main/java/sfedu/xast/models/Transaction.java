package sfedu.xast.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import sfedu.xast.utils.Status;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "transactionId", nullable = false, updatable = false)
    private String transactionId;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "changeid",
            referencedColumnName = "exchangeid",
            nullable = false
    )
    private SkillExchange changeId;

    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = new Date();
        }
    }

    public Transaction(Status status, SkillExchange changeId) {
        this.status = Status.valueOf(status.name());
        this.changeId = changeId;
    }

    public Transaction() {}

}
