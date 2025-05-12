package sfedu.xast.srtategyTask.SingleTable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sfedu.xast.srtategyTask.JoinedTable.PersonJT;
import sfedu.xast.srtategyTask.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class STPersonRepo implements PersonRepository<PersonST>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PersonST findById(String id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public List<PersonST> findAll() {
        return entityManager.createQuery("SELECT p FROM PersonST p", PersonST.class).getResultList();
    }

    @Override
    @Transactional
    public PersonST save(PersonST entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public PersonST update(PersonST entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(String id) {
        PersonST person = findById(id);
        if (person != null) {
            entityManager.remove(person);
        }
    }

    @Override
    public List<PersonST> findBySurname(String surname) {
        return entityManager.createQuery(
                        "SELECT p FROM PersonST p WHERE p.surname = :surname", PersonST.class)
                .setParameter("surname", surname)
                .getResultList();
    }

    // Дополнительные методы для конкретных типов
    @Transactional
    public List<PersInfST> findAllPersInf() {
        return entityManager.createQuery(
                        "SELECT p FROM PersInf p", PersInfST.class)
                .getResultList();
    }

    @Transactional
    public List<ProfInfST> findAllProfInf() {
        return entityManager.createQuery(
                        "SELECT p FROM ProfInf p", ProfInfST.class)
                .getResultList();
    }

}
