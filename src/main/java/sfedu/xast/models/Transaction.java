package sfedu.xast.models;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import sfedu.xast.utils.Status;
import java.util.*;

@Getter
@Setter
public class Transaction {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "transactionId", nullable = false, updatable = false)
    private String transactionId;

    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private Status status;

    @OneToOne
    @JoinColumn(name = "changeid",
            referencedColumnName = "exchangeid",
            nullable = false
    )
    private SkillExchange changeId;

    public Transaction(Status status, SkillExchange changeId) {
        this.transactionId = UUID.randomUUID().toString();
        this.date = new Date();
        this.status = Status.valueOf(status.name());
        this.changeId = changeId;
    }
}
