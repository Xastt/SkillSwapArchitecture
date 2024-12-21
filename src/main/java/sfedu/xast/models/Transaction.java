package sfedu.xast.models;

import lombok.Getter;
import lombok.Setter;
import sfedu.xast.Status;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Transaction {
    private String transactionId;
    private Date date;
    private Status status;
    private String changeId;

    public Transaction(Status status, String changeId) {
        this.transactionId = UUID.randomUUID().toString();
        this.date = new Date();
        this.status = Status.valueOf(status.name());
        this.changeId = changeId;
    }
}
