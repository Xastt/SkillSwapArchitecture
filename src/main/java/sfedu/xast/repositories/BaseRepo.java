package sfedu.xast.repositories;

import org.hibernate.Session;
import sfedu.xast.utils.HibernateUtil;

public abstract class BaseRepo<T, ID> {

    private final Class<T> entityClass;

    protected BaseRepo(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public T findById(ID id) {
        try (Session session = getSession()) {
            return session.get(entityClass, id);
        }
    }

    public T save(T entity) {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    public T update(T entity) {
        try (Session session = getSession()) {
            session.beginTransaction();
            T mergedEntity = session.merge(entity);
            session.getTransaction().commit();
            return mergedEntity;
        }
    }

    public void delete(ID id) {
        try (Session session = getSession()) {
            session.beginTransaction();
            T entity = session.get(entityClass, id);
            if(entity != null) {
                session.remove(entity);
            }
            session.getTransaction().commit();
        }
    }
}
