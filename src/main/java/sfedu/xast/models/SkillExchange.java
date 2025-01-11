package sfedu.xast.models;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
public class SkillExchange {
    private String exchangeId;
    private String skillOffered;
    private String userOffering;
    private String userRequesting;

    public SkillExchange(String skillOffered, String userRequesting, String userOffering) {
        this.exchangeId = UUID.randomUUID().toString();
        this.skillOffered = skillOffered;
        this.userRequesting = userRequesting;
        this.userOffering = userOffering;
    }
}
