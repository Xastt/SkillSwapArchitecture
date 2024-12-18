package sfedu.xast.models;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
public class SkillExchange {
    private String exchangeId;
    private ProfInf skillOffered;
    private PersInf userOffering;
    private PersInf userRequesting;

    public SkillExchange(ProfInf skillOffered, PersInf userRequesting, PersInf userOffering) {
        this.exchangeId = UUID.randomUUID().toString();
        this.skillOffered = skillOffered;
        this.userRequesting = userRequesting;
        this.userOffering = userOffering;
    }
}
