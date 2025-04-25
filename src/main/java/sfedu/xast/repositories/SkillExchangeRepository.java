package sfedu.xast.repositories;

import org.hibernate.Session;
import sfedu.xast.models.SkillExchange;

import java.util.List;

public class SkillExchangeRepository extends BaseRepo<SkillExchange, String> {

    protected SkillExchangeRepository(Class<SkillExchange> entityClass) {
        super(entityClass);
    }

}
