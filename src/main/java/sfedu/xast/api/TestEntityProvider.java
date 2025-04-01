package sfedu.xast.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sfedu.xast.models.TestEntity;

public class TestEntityProvider {

    private final SessionFactory sessionFactory;

    public TestEntityProvider(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveEntity(TestEntity entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }
    }

    public TestEntity getEntityById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(TestEntity.class, id);
        }
    }

    public void updateEntity(TestEntity entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }
    }

    public void deleteEntity(TestEntity entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }
}