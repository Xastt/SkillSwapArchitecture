package sfedu.xast.srtategyTask.crud;

public interface PersonRepository<T> {
    void saveEntity(T entity);
    T getEntityById(Long id);
    void updateEntity(T entity);
    void deleteEntity(T entity);
}