package sfedu.xast.srtategyTask.MappedSuperclass;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import sfedu.xast.srtategyTask.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MSPersonRepo implements PersonRepository<PersonMS> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PersonMS findById(String id) {
        // Пробуем найти во всех таблицах
        PersonMS person = entityManager.find(PersInfMS.class, id);
        if (person == null) {
            person = entityManager.find(ProfInfMS.class, id);
        }
        return person;
    }

    @Override
    public List<PersonMS> findAll() {
        List<PersonMS> result = new ArrayList<>();
        result.addAll(entityManager.createQuery("SELECT p FROM PersInf p", PersInfMS.class).getResultList());
        result.addAll(entityManager.createQuery("SELECT p FROM ProfInf p", ProfInfMS.class).getResultList());
        return result;
    }

    @Override
    @Transactional
    public PersonMS save(PersonMS entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public PersonMS update(PersonMS entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(String id) {
        PersonMS person = findById(id);
        if (person != null) {
            entityManager.remove(person);
        }
    }

    @Override
    public List<PersonMS> findBySurname(String surname) {
        List<PersonMS> result = new ArrayList<>();
        result.addAll(entityManager.createQuery(
                        "SELECT p FROM PersInf p WHERE p.surname = :surname", PersInfMS.class)
                .setParameter("surname", surname)
                .getResultList());
        result.addAll(entityManager.createQuery(
                        "SELECT p FROM ProfInf p WHERE p.surname = :surname", ProfInfMS.class)
                .setParameter("surname", surname)
                .getResultList());
        return result;
    }
}
