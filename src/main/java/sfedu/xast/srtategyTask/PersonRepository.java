package sfedu.xast.srtategyTask;

import java.util.List;

public interface PersonRepository <T>{
    T findById(String id);
    List<T> findAll();
    T save(T entity);
    T update(T entity);
    void delete(String id);
    List<T> findBySurname(String surname);
}
