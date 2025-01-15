package sfedu.xast.api;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import org.slf4j.*;
import sfedu.xast.models.PersInf;
import sfedu.xast.utils.Constants;

import java.io.*;
import java.nio.file.*;
import java.util.*;

//TODO 1)добавить проверку возвращаемых значений в тестах 2)boolean + проверка на null. 3) Исправить методы так, чтобы возвращался не список, а писалось в объект или обыграть это иначе

public class DataProviderCsv  {

    Logger logger = LoggerFactory.getLogger(DataProviderCsv.class);

    /**
     * write records in csv file
     * @param data
     * @throws IOException
     */
    public static void writeToCsv(List<String[]> data, String csvFilePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFilePath));
             CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeAll(data);
        }
    }

    /**
     * read records from csv file
     * @return
     * @throws IOException
     * @throws CsvException
     */
    public  List<String[]> readFromCsv(String csvFilePath) throws IOException, CsvException {
        List<String[]> data = new ArrayList<>();
        if (Files.exists(Paths.get(csvFilePath))) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                 CSVReader csvReader = new CSVReader(reader)) {
                data = csvReader.readAll();
            }
        }
        return data;
    }

    /**
     * creating record in csv file
     * @param persInf
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean createPersInf(PersInf persInf, String csvFilePath) throws IOException, CsvException {
        if(persInf==null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.add(new String[]{persInf.getId(), persInf.getSurname(), persInf.getName(),
                    persInf.getPhoneNumber(), persInf.getEmail()});
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            e.printStackTrace();
            //logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from csv file using personal id
     * @param persInf
     * @param id
     * @return PersINf object
     * @throws IOException
     * @throws CsvException
     */
    public PersInf readPersInf(PersInf persInf, String id, String csvFilePath) throws IOException, CsvException {
        if(persInf==null){
            throw new CsvException("PersInf object must not be null");
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
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

    /**
     * updating records in csv file by personal id
     * @param updatedPersInf
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean updatePersInf(PersInf updatedPersInf, String csvFilePath) throws IOException, CsvException {
        if (updatedPersInf == null) {
            throw new CsvException("PersInf object must not be null");
        }
        try {
            List<String[]> data = readFromCsv(csvFilePath);
            boolean found = false;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[0].equals(updatedPersInf.getId())) {
                    data.set(i, new String[]{updatedPersInf.getId(), updatedPersInf.getSurname(),
                            updatedPersInf.getName(), updatedPersInf.getPhoneNumber(), updatedPersInf.getEmail()});
                    found = true;
                    break;
                }
            }
            writeToCsv(data, csvFilePath);
            return found;
        }catch (CsvException | IOException e){
            e.printStackTrace();
            //logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * delete records from csv file using id
     * @param id
     * @return
     */
    public boolean deletePersInf(String id, String csvFilePath) {
        if(id == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.removeIf(row -> row[0].equals(id));
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            e.printStackTrace();
            //logger.error(e.getMessage());
            return false;
        }

    }
}
