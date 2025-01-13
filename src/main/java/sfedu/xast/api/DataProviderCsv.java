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

public class DataProviderCsv  {

    //works
    public static void writeToCsv(List<String[]> data) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(Constants.csvFilePath));
             CSVWriter csvWriter = new CSVWriter(writer)) {
            csvWriter.writeAll(data);
        }
    }

    public static List<String[]> readFromCsv() throws IOException, CsvException {
        List<String[]> data = new ArrayList<>();
        if (Files.exists(Paths.get(Constants.csvFilePath))) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(Constants.csvFilePath));
                 CSVReader csvReader = new CSVReader(reader)) {
                data = csvReader.readAll();
            }
        }
        return data;
    }

    //works
    public static void printCsvData() throws IOException, CsvException {
        List<String[]> data = readFromCsv();
        for (String[] row : data) {
            System.out.println(String.join(", ", row));
        }
    }


    // CRUD methods for PersInf
    //works
    public void createPersInf(PersInf persInf) throws IOException, CsvException {
        List<String[]> data = readFromCsv();
        data.add(new String[]{persInf.getId(), persInf.getSurname(), persInf.getName(),
                persInf.getPhoneNumber(), persInf.getEmail()});
        writeToCsv(data);
    }

    //not works
    public List<String[]> readPersInf() throws IOException, CsvException {
        return readFromCsv();
    }

    //not works
    public void updatePersInf(PersInf updatedPersInf) throws IOException, CsvException {
        List<String[]> data = readFromCsv();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)[0].equals(updatedPersInf.getId())) {
                data.set(i, new String[]{updatedPersInf.getId(), updatedPersInf.getSurname(),
                        updatedPersInf.getName(), updatedPersInf.getPhoneNumber(), updatedPersInf.getEmail()});
                break;
            }
        }
        writeToCsv(data);
    }

    //works
    public void deletePersInf(String id) throws IOException, CsvException {
        List<String[]> data = readFromCsv();
        data.removeIf(row -> row[0].equals(id));
        writeToCsv(data);
    }

    public static void main(String[] args) throws IOException, CsvException {
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
       dataProviderCsv.deletePersInf("ddfd19ca-881c-4acf-97f4-32389be4296b");
    }
}
