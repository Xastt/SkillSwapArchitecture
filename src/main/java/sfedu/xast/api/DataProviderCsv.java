package sfedu.xast.api;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.*;
import sfedu.xast.models.PersInf;
import java.io.*;
import java.util.*;

public class DataProviderCsv implements IDataProvider<PersInf> {

    Logger logger = LoggerFactory.getLogger(DataProviderCsv.class);

    private String csvFilePath;
    private List<PersInf> persInfList;

    public DataProviderCsv(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        initDataSource();
    }

    @Override
    public void saveRecord(PersInf record) {
        try(CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath, true))) {
            String[] values = {String.valueOf(record.getId()), record.getName()};
            writer.writeNext(values);
            persInfList.add(record);
        }catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void deleteRecord(Long id) {
        persInfList.removeIf(record -> record.getId().equals(id));
        try(CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))){
            for(PersInf record : persInfList){
                String[] values = {String.valueOf(record.getId()), record.getName()};
                writer.writeNext(values);
            }
        }catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

    @Override
    public PersInf getRecordById(Long id) {
        return persInfList.stream().filter(record -> record.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void initDataSource() {
        persInfList = new ArrayList<>();
        File file = new File(csvFilePath);
        if(!file.exists()) {
            try{
                file.createNewFile();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }else{
            try(CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
                String[] nextLine;
                while((nextLine = reader.readNext())!=null){
                    Long id = Long.parseLong(nextLine[0]);
                    String name = nextLine[1];
                    persInfList.add(new PersInf(id, name));
                }
            }catch (IOException | CsvValidationException e){
                logger.error(e.getMessage());
            }
        }
    }
}
