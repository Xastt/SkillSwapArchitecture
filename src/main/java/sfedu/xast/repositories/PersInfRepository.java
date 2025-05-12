package sfedu.xast.repositories;

import org.hibernate.Session;
import sfedu.xast.models.PersInf;

public class PersInfRepository extends BaseRepo <PersInf, String> {

    protected PersInfRepository(Class<PersInf> entityClass) {
        super(entityClass);
    }

    public PersInf findByEmail(String email) {
        try (Session session = getSession()) {
            return session.createQuery(
                            "SELECT p FROM PersInfMS p WHERE p.email = :email", PersInf.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
    }

}
