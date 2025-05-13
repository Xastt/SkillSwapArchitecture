package sfedu.xast.repositories;

import org.hibernate.Session;
import sfedu.xast.models.ProfInf;

import java.util.List;

public class ProfInfRepository extends BaseRepo<ProfInf, String> {

    protected ProfInfRepository(Class<ProfInf> entityClass) {
        super(entityClass);
    }

    public List<ProfInf> findByPersId(String persId) {
        try (Session session = getSession()) {
            return session.createQuery(
                            "SELECT p FROM ProfInf p WHERE p.pers.id = :persId", ProfInf.class)
                    .setParameter("persId", persId)
                    .getResultList();
        }
    }

    public List<ProfInf> findBySkillName(String skillName) {
        try (Session session = getSession()) {
            return session.createQuery(
                            "SELECT p FROM ProfInf p WHERE p.skillName LIKE :skillName", ProfInf.class)
                    .setParameter("skillName", "%" + skillName + "%")
                    .getResultList();
        }
    }
}
