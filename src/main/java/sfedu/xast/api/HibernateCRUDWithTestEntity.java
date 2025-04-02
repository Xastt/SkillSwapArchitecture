package sfedu.xast.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sfedu.xast.models.TestEntity;

public class HibernateCRUDWithTestEntity {

    private final SessionFactory sessionFactory;

    public HibernateCRUDWithTestEntity(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Long createTestEntity(TestEntity entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Long id = (Long) session.save(entity);
            transaction.commit();
            return id;
        }
    }

    public TestEntity readTestEntity(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(TestEntity.class, id);
        }
    }

    public void updateTestEntity(TestEntity entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        }
    }

    public void deleteTestEntity(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            TestEntity entity = session.get(TestEntity.class, id);
            if (entity != null) {
                session.delete(entity);
            }
            transaction.commit();
        }
    }

    public boolean existsById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            TestEntity entity = session.get(TestEntity.class, id);
            return entity != null;
        }
    }
}
