package sfedu.xast.models;

import lombok.*;
import sfedu.xast.utils.Status;
import java.util.*;

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
