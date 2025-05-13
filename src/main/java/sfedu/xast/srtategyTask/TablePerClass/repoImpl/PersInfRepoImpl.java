package sfedu.xast.srtategyTask.TablePerClass.repoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sfedu.xast.srtategyTask.TablePerClass.PersInf;
import sfedu.xast.srtategyTask.crud.PersInfRepository;

public class PersInfRepoImpl implements PersInfRepository<PersInf> {

    private final SessionFactory sessionFactory;

    public PersInfRepoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveEntity(PersInf entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }

    }

    @Override
    public PersInf getEntityById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(PersInf.class, id);
        }
    }

    @Override
    public void updateEntity(PersInf entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }

    }

    @Override
    public void deleteEntity(PersInf entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }
}
