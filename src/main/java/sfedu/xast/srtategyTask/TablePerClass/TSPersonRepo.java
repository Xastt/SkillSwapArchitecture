package sfedu.xast.srtategyTask.TablePerClass;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sfedu.xast.srtategyTask.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class TSPersonRepo implements PersonRepository<PersonTPC> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PersonTPC findById(String id) {
        return entityManager.find(PersonTPC.class, id);
    }

    @Override
    public List<PersonTPC> findAll() {
        return entityManager.createQuery("SELECT p FROM PersonTPC p", PersonTPC.class).getResultList();
    }

    @Override
    @Transactional
    public PersonTPC save(PersonTPC entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public PersonTPC update(PersonTPC entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(String id) {
        PersonTPC person = findById(id);
        if (person != null) {
            entityManager.remove(person);
        }
    }

    @Override
    public List<PersonTPC> findBySurname(String surname) {
        return entityManager.createQuery(
                        "SELECT p FROM PersonTPC p WHERE p.surname = :surname", PersonTPC.class)
                .setParameter("surname", surname)
                .getResultList();
    }
}
