package sfedu.xast.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import sfedu.xast.models.PersInf;
import sfedu.xast.models.ProfInf;

import java.util.List;

public class ProfInfRepository extends BaseRepo<ProfInf, String> {

    public ProfInfRepository(Class<ProfInf> entityClass) {
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

    public List<Object[]> getUserSkillsSummaryCriteria() {
        try (Session session = getSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

            Root<PersInf> pers = cq.from(PersInf.class);
            Join<PersInf, ProfInf> prof = pers.join("providedSkills");

            cq.multiselect(
                    pers.get("surname"),
                    pers.get("name"),
                    cb.count(prof),
                    cb.avg(prof.get("rating"))
            );

            cq.groupBy(pers.get("id"), pers.get("surname"), pers.get("name"));

            return session.createQuery(cq).getResultList();
        }
    }
}
