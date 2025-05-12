package sfedu.xast.srtategyTask.JoinedTable;

import sfedu.xast.srtategyTask.PersonRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class JTPersonRepo implements PersonRepository<PersonJT> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PersonJT findById(String id) {
        return entityManager.find(PersonJT.class, id);
    }

    @Override
    public List<PersonJT> findAll() {
        return entityManager.createQuery("SELECT p FROM PersonJT p", PersonJT.class).getResultList();
    }

    @Override
    @Transactional
    public PersonJT save(PersonJT entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public PersonJT update(PersonJT entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(String id) {
        PersonJT person = findById(id);
        if (person != null) {
            entityManager.remove(person);
        }
    }

    @Override
    public List<PersonJT> findBySurname(String surname) {
        return entityManager.createQuery(
                        "SELECT p FROM PersonJT p WHERE p.surname = :surname", PersonJT.class)
                .setParameter("surname", surname)
                .getResultList();
    }

    // Дополнительные методы для работы со связями
    @Transactional
    public void addProfInfToPersInf(String persInfId, ProfInfJT profInf) {
        PersInfJT persInf = entityManager.find(PersInfJT.class, persInfId);
        if (persInf != null) {
            profInf.setPers(persInf);
            save(profInf);
            persInf.getProvidedSkills().add(profInf);
            entityManager.merge(persInf);
        }
    }
}
