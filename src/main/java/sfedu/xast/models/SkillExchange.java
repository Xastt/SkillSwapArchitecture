package sfedu.xast.models;

import lombok.Getter;
import lombok.Setter;

//ID Add
@Getter
@Setter
public class SkillExchange {
    private int exchangeId;
    private PersInf skillOffered;
    private PersInf skillRequested;
    private PersInf userOffering;
    private PersInf userRequesting;

    public SkillExchange(int exchangeId, PersInf skillOffered, PersInf skillRequested,
                         PersInf userRequesting, PersInf userOffering) {
        this.exchangeId = exchangeId;
        this.skillOffered = skillOffered;
        this.skillRequested = skillRequested;
        this.userRequesting = userRequesting;
        this.userOffering = userOffering;
    }
}
