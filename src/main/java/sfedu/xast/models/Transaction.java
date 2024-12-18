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
    private String user1;
    private String user2;
    private String skillOffered;

    public Transaction(Date date, Status status,
                       String user1, String user2, String skillOffered) {
        this.transactionId = UUID.randomUUID().toString();
        this.date = date;
        this.status = status;
        this.user1 = user1;
        this.user2 = user2;
        this.skillOffered = skillOffered;
    }
}
