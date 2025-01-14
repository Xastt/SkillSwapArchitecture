package sfedu.xast.api;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.*;
import sfedu.xast.models.PersForApi;
import sfedu.xast.models.PersInf;
import sfedu.xast.utils.Constants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

//TODO 1)добавить проверку возвращаемых значений в тестах 2)boolean + проверка на null. 3) Исправить методы так, чтобы возвращался не список, а писалось в объект или обыграть это иначе

public class DataProviderCsv  {

    Logger logger = LoggerFactory.getLogger(DataProviderCsv.class);

    public static void writeToCsv(List<String[]> data) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(Constants.csvFilePath));
             CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeAll(data);
        }
    }

    public  List<String[]> readFromCsv() throws IOException, CsvException {
        List<String[]> data = new ArrayList<>();
        if (Files.exists(Paths.get(Constants.csvFilePath))) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(Constants.csvFilePath));
                 CSVReader csvReader = new CSVReader(reader)) {
                data = csvReader.readAll();
            }
        }
        return data;
    }

    public boolean createPersInf(PersInf persInf) throws IOException, CsvException {
        if(persInf==null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv();
            data.add(new String[]{persInf.getId(), persInf.getSurname(), persInf.getName(),
                    persInf.getPhoneNumber(), persInf.getEmail()});
            writeToCsv(data);
            return true;
        }catch (CsvException | IOException e){
            e.printStackTrace();
            //logger.error(e.getMessage());
            return false;
        }
    }

    public PersInf readPersInf(PersInf persInf, String id) throws IOException, CsvException {
        if(persInf==null){
            throw new CsvException("PersInf object must not be null");
        }
        try{
            List<String[]> data = readFromCsv();
            for (String[] row : data){
                if(row[0].equals(id)){
                    persInf.setId(row[0]);
                    persInf.setSurname(row[1]);
                    persInf.setName(row[2]);
                    persInf.setPhoneNumber(row[3]);
                    persInf.setEmail(row[4]);
                    return persInf;
                }
            }
            throw new CsvException("Can't find person with id " + id);
        }catch (CsvException | IOException e){
            e.printStackTrace();
            //logger.error(e.getMessage());
            throw e;
        }
    }

    public boolean updatePersInf(PersInf updatedPersInf) throws IOException, CsvException {
        try {
            List<String[]> data = readFromCsv();
            boolean found = false;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[0].equals(updatedPersInf.getId())) {
                    data.set(i, new String[]{updatedPersInf.getId(), updatedPersInf.getSurname(),
                            updatedPersInf.getName(), updatedPersInf.getPhoneNumber(), updatedPersInf.getEmail()});
                    found = true;
                    break;
                }
            }
            writeToCsv(data);
            return found;
        }catch (CsvException | IOException e){
            e.printStackTrace();
            //logger.error(e.getMessage());
            return false;
        }
    }

    public boolean deletePersInf(String id) {
        try{
            List<String[]> data = readFromCsv();
            data.removeIf(row -> row[0].equals(id));
            writeToCsv(data);
            return true;
        }catch (CsvException | IOException e){
            e.printStackTrace();
            //logger.error(e.getMessage());
            return false;
        }

    }
}
