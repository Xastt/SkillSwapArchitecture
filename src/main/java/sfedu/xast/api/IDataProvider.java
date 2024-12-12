package sfedu.xast.api;

public interface IDataProvider <T>{
    void saveRecord(T record);
    void deleteRecord(Long id);
    T getRecordById(Long id);
    void initDataSource();
}
