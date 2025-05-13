package sfedu.xast.srtategyTask.MappedSuperclass.repoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sfedu.xast.srtategyTask.MappedSuperclass.Person;
import sfedu.xast.srtategyTask.crud.PersonRepository;

public class PersonRepoImpl implements PersonRepository<Person> {

    private final SessionFactory sessionFactory;

    public PersonRepoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveEntity(Person entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }
    }

    @Override
    public Person getEntityById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Person.class, id);
        }
    }

    @Override
    public void updateEntity(Person entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }

    }

    @Override
    public void deleteEntity(Person entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }

}
