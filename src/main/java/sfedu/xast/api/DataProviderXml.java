package sfedu.xast.api;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.*;
import sfedu.xast.models.*;
import java.io.*;
import java.util.*;

public class DataProviderXml implements IDataProvider <PersForApi>{

    Logger logger = LoggerFactory.getLogger(DataProviderXml.class);

    private String xmlFilePath;
    private List<PersForApi> persInfList;

    public DataProviderXml(String xmlFilePath){
        this.xmlFilePath = xmlFilePath;
        initDataSource();
    }

    @Override
    public void saveRecord(PersForApi record) {
        persInfList.add(record);
        Serializer serializer = new Persister();
        PersInfList persInfWrapper = new PersInfList(persInfList);
        try{
            serializer.write(persInfWrapper, new File(xmlFilePath));
        }catch (Exception e){
            logger.error("SAVE RECORD {}", e.getMessage());
        }
    }

    @Override
    public void deleteRecord(Long id) {
        persInfList.removeIf(record -> record.getId().equals(id));
        Serializer serializer = new Persister();
        PersInfList persInfWrapper = new PersInfList(persInfList);
        try{
            serializer.write(persInfWrapper, new File(xmlFilePath));
        }catch (Exception e){
            logger.error("DELETE RECORD {}", e.getMessage());
        }
    }

    @Override
    public PersForApi getRecordById(Long id) {
        return persInfList.stream().filter(record -> record.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void initDataSource() {
        persInfList = new ArrayList<>();
        File file = new File(xmlFilePath);
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch(IOException e){
                logger.error(e.getMessage());
            }
        }else{
            Serializer serializer = new Persister();
            try{
                PersInfList loadedRecords = serializer.read(PersInfList.class, file);
                persInfList.addAll(loadedRecords.getRecords());
            }catch(Exception e){
                logger.error("INIT RECORD {}", e.getMessage());
            }
        }
    }
}
