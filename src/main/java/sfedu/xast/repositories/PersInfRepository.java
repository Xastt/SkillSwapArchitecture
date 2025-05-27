package sfedu.xast.repositories;

import org.hibernate.Session;
import sfedu.xast.models.PersInf;

import java.util.List;

public class PersInfRepository extends BaseRepo <PersInf, String> {

    public PersInfRepository(Class<PersInf> entityClass) {
        super(entityClass);
    }

    public PersInf findByEmail(String email) {
        try (Session session = getSession()) {
            return session.createQuery(
                            "SELECT p FROM PersInf p WHERE p.email = :email", PersInf.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

    public List<Object[]> getUserSkillsSummaryNative() {
        try (Session session = getSession()) {
            return session.createNativeQuery(
                    "SELECT p.surname, p.name, COUNT(pr.id) as skill_count, AVG(pr.rating) as avg_rating " +
                            "FROM persinf p JOIN profinf pr ON p.id = pr.pers_id " +
                            "GROUP BY p.id, p.surname, p.name").getResultList();
        }
    }

    public List<Object[]> getUserSkillsSummaryHQL() {
        try (Session session = getSession()) {
            return session.createQuery(
                    "SELECT p.surname, p.name, COUNT(pr), AVG(pr.rating) " +
                            "FROM PersInf p JOIN p.providedSkills pr " +
                            "GROUP BY p.id, p.surname, p.name").getResultList();
        }
    }

}
