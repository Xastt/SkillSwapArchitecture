package sfedu.xast.repositories;

import sfedu.xast.models.SkillExchange;

public class SkillExchangeRepository extends BaseRepo<SkillExchange, String> {

    protected SkillExchangeRepository(Class<SkillExchange> entityClass) {
        super(entityClass);
    }

}
