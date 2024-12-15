package sfedu.xast.models;

import lombok.Getter;
import lombok.Setter;
import sfedu.xast.Status;

import java.util.Date;

//ID
@Getter
@Setter
public class Transaction {
    private int transactionId;
    private Date date;
    private Status status;
    private PersInf user1;
    private PersInf user2;
    private PersInf skillOffered;
    private PersInf skillRequested;

    public Transaction(int transactionId, Date date, Status status,
                       PersInf user1, PersInf user2, PersInf skillRequested, PersInf skillOffered) {
        this.transactionId = transactionId;
        this.date = date;
        this.status = status;
        this.user1 = user1;
        this.user2 = user2;
        this.skillRequested = skillRequested;
        this.skillOffered = skillOffered;
    }
}
