package sfedu.xast.srtategyTask.MappedSuperclass.repoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sfedu.xast.srtategyTask.MappedSuperclass.ProfInf;
import sfedu.xast.srtategyTask.crud.ProfInfRepository;

public class ProfInfRepoImpl implements ProfInfRepository<ProfInf> {

    private final SessionFactory sessionFactory;

    public ProfInfRepoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveEntity(ProfInf entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }
    }

    @Override
    public ProfInf getEntityById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(ProfInf.class, id);
        }
    }

    @Override
    public void updateEntity(ProfInf entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }

    }

    @Override
    public void deleteEntity(ProfInf entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }
}
