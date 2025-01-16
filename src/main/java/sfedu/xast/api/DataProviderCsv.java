package sfedu.xast.api;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import org.slf4j.*;
import sfedu.xast.models.*;
import sfedu.xast.utils.Status;

import java.io.*;
import java.nio.file.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
     * @param csvFilePath
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
            data.add(new String[]{
                    persInf.getId(),
                    persInf.getSurname(),
                    persInf.getName(),
                    persInf.getPhoneNumber(),
                    persInf.getEmail()
            });
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from csv file using personal id
     * @param persInf
     * @param id
     * @param csvFilePath
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
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in csv file by personal id
     * @param updatedPersInf
     * @param csvFilePath
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
                    data.set(i, new String[]{
                            updatedPersInf.getId(),
                            updatedPersInf.getSurname(),
                            updatedPersInf.getName(),
                            updatedPersInf.getPhoneNumber(),
                            updatedPersInf.getEmail()
                    });
                    found = true;
                    break;
                }
            }
            writeToCsv(data, csvFilePath);
            return found;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * delete records from csv file using id
     * @param id
     * @param csvFilePath
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
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * creating record in csv file
     * @param persInf
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean createProfInf(ProfInf profInf, PersInf persInf, String csvFilePath) throws IOException, CsvException {
        if(persInf == null || profInf   == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.add(new String[]{
                    persInf.getId(),
                    profInf.getSkillName(),
                    profInf.getSkillDescription(),
                    String.valueOf(profInf.getCost()),
                    profInf.getPersDescription(),
                    String.valueOf(profInf.getExp()),
                    String.valueOf(profInf.getRating())
            });
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from csv file using personal id
     * @param profInf
     * @param id
     * @param csvFilePath
     * @return PersINf object
     * @throws IOException
     * @throws CsvException
     */
    public ProfInf readProfInf(ProfInf profInf, String id, String csvFilePath) throws IOException, CsvException {
        if(profInf == null){
            throw new CsvException("ProfInf object must not be null");
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            for (String[] row : data){
                if(row[0].equals(id)){
                    profInf.setSkillName(row[1]);
                    profInf.setSkillDescription(row[2]);
                    profInf.setCost(Double.valueOf(row[3]));
                    profInf.setPersDescription(row[4]);
                    profInf.setExp(Double.valueOf(row[5]));
                    profInf.setRating(Double.valueOf(row[6]));
                    return profInf;
                }
            }
            throw new CsvException("Can't find person with id " + id);
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * read information from table profinf using id
     * @param id
     * @return
     * @throws SQLException
     */
    public ProfInf readProfInfWithId(String id, String csvFilePath) throws IOException, CsvException {
        ProfInf profInf = new ProfInf();
        if(profInf == null){
            throw new CsvException("ProfInf object must not be null");
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            for (String[] row : data){
                if(row[0].equals(id)){
                    profInf.setSkillName(row[1]);
                    profInf.setSkillDescription(row[2]);
                    profInf.setCost(Double.valueOf(row[3]));
                    profInf.setPersDescription(row[4]);
                    profInf.setExp(Double.valueOf(row[5]));
                    profInf.setRating(Double.valueOf(row[6]));
                    return profInf;
                }
            }
            throw new CsvException("Can't find person with id " + id);
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in csv file by personal id
     * @param updatedProfInf
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean updateProfInf(ProfInf updatedProfInf, String csvFilePath) throws IOException, CsvException {
        if (updatedProfInf == null) {
            throw new CsvException("ProfInf object must not be null");
        }
        try {
            List<String[]> data = readFromCsv(csvFilePath);
            boolean found = false;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[0].equals(updatedProfInf.getPersId())) {
                    data.set(i, new String[]{
                            updatedProfInf.getPersId(),
                            updatedProfInf.getSkillName(),
                            updatedProfInf.getSkillDescription(),
                            String.valueOf(updatedProfInf.getCost()),
                            updatedProfInf.getPersDescription(),
                            String.valueOf(updatedProfInf.getExp()),
                            String.valueOf(updatedProfInf.getRating())
                    });
                    found = true;
                    break;
                }
            }
            writeToCsv(data, csvFilePath);
            return found;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * delete records from csv file using id
     * @param id
     * @param csvFilePath
     * @return
     */
    public boolean deleteProfInf(String id, String csvFilePath) {
        if(id == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.removeIf(row -> row[0].equals(id));
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * method, which add new data to the csv file skillExchange
     * contains information about users and offering skill
     * @param skillExchange
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean createSkillExchange(SkillExchange skillExchange, String csvFilePath) throws IOException, CsvException {
        if(skillExchange == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.add(new String[]{
                    skillExchange.getExchangeId(),
                    skillExchange.getSkillOffered(),
                    skillExchange.getUserOffering(),
                    skillExchange.getUserRequesting()
            });
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from csv file using personal id
     * @param skillExchange
     * @param csvFilePath
     * @return PersINf object
     * @throws IOException
     * @throws CsvException
     */
    public SkillExchange readSkillExchange(SkillExchange skillExchange, String csvFilePath) throws IOException, CsvException {
        if(skillExchange == null){
            throw new CsvException("SkillExchange object must not be null");
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            for (String[] row : data){
                if(row[0].equals(skillExchange.getExchangeId())){
                    skillExchange.setExchangeId(row[0]);
                    skillExchange.setSkillOffered(row[1]);
                    skillExchange.setUserOffering(row[2]);
                    skillExchange.setUserRequesting(row[3]);
                    return skillExchange;
                }
            }
            throw new CsvException("Can't find person with id " + skillExchange.getExchangeId());
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in csv file by personal id
     * @param skillExchange
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean updateSkillExchange(SkillExchange skillExchange, String csvFilePath) throws IOException, CsvException {
        if (skillExchange == null) {
            throw new CsvException("SkillExchange object must not be null");
        }
        try {
            List<String[]> data = readFromCsv(csvFilePath);
            boolean found = false;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[0].equals(skillExchange.getExchangeId())) {
                    data.set(i, new String[]{
                            skillExchange.getExchangeId(),
                            skillExchange.getSkillOffered(),
                            skillExchange.getUserOffering(),
                            skillExchange.getUserRequesting(),
                            skillExchange.getExchangeId()
                    });
                    found = true;
                    break;
                }
            }
            writeToCsv(data, csvFilePath);
            return found;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * delete records from csv file using id
     * @param id
     * @param csvFilePath
     * @return
     */
    public boolean deleteSkillExchange(String id, String csvFilePath) {
        if(id == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.removeIf(row -> row[0].equals(id));
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }

    }
    /**
     * method, which add new data to the csv file Review
     * contains information about users and offering skill
     * @param review
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean createReview(Review review, String csvFilePath) throws IOException, CsvException {
        if(review == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.add(new String[]{
                    review.getReviewId(),
                    String.valueOf(review.getRating()),
                    review.getComment(),
                    review.getReviewer(),
                    review.getUserEvaluated()
            });
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from csv file using id
     * @param review
     * @param csvFilePath
     * @return PersINf object
     * @throws IOException
     * @throws CsvException
     */
    public Review readReview(Review review, String csvFilePath) throws IOException, CsvException {
        if(review == null){
            throw new CsvException("Review object must not be null");
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            for (String[] row : data){
                if(row[0].equals(review.getReviewId())){
                    review.setReviewId(row[0]);
                    review.setRating(Double.valueOf(row[1]));
                    review.setComment(row[2]);
                    review.setReviewer(row[3]);
                    review.setUserEvaluated(row[4]);
                    return review;
                }
            }
            throw new CsvException("Can't find review with id " + review.getReviewId());
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in csv file by review id
     * @param review
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean updateReview(Review review, String csvFilePath) throws IOException, CsvException {
        if (review == null) {
            throw new CsvException("Review object must not be null");
        }
        try {
            List<String[]> data = readFromCsv(csvFilePath);
            boolean found = false;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[0].equals(review.getReviewId())) {
                    data.set(i, new String[]{
                            review.getReviewId(),
                            String.valueOf(review.getRating()),
                            review.getComment(),
                            review.getReviewer(),
                            review.getUserEvaluated()
                    });
                    found = true;
                    break;
                }
            }
            writeToCsv(data, csvFilePath);
            return found;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * delete records from csv file using reviewId
     * @param review
     * @param csvFilePath
     * @return
     */
    public boolean deleteReview(Review review, String csvFilePath) {
        if(review == null || review.getReviewId() == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.removeIf(row -> row[0].equals(review.getReviewId()));
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }
    /**
     * method, which add new data to the csv file Review
     * contains information about users and offering skill
     * @param transaction
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean createTransaction(Transaction transaction, String csvFilePath) throws IOException, CsvException {
        if(transaction == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.add(new String[]{
                    transaction.getTransactionId(),
                    String.valueOf(transaction.getDate()),
                    String.valueOf(transaction.getStatus()),
                    transaction.getChangeId()
            });
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * reading records from csv file using id
     * @param transaction
     * @param csvFilePath
     * @return PersINf object
     * @throws IOException
     * @throws CsvException
     */
    public Transaction readTransaction(Transaction transaction, String csvFilePath) throws IOException, CsvException {
        if(transaction == null){
            throw new CsvException("Transaction object must not be null");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("MSK"));
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            for (String[] row : data){
                if(row[0].equals(transaction.getTransactionId())){
                    transaction.setTransactionId(row[0]);
                    try{
                        Date date = dateFormat.parse(row[1]);
                        transaction.setDate(date);
                    }catch (ParseException e){
                        throw new CsvException("Invalid date format for transaction with id " + transaction.getTransactionId());
                    }
                    transaction.setStatus(Status.valueOf(row[2]));
                    transaction.setChangeId(row[3]);
                    return transaction;
                }
            }
            throw new CsvException("Can't find transaction with id " + transaction.getTransactionId());
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * updating records in csv file by transaction id
     * @param transaction
     * @param csvFilePath
     * @return true or false
     * @throws IOException
     * @throws CsvException
     */
    public boolean updateTransaction(Transaction transaction, String csvFilePath) throws IOException, CsvException {
        if (transaction == null) {
            throw new CsvException("Transaction object must not be null");
        }
        try {
            List<String[]> data = readFromCsv(csvFilePath);
            boolean found = false;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i)[0].equals(transaction.getTransactionId())) {
                    data.set(i, new String[]{
                            transaction.getTransactionId(),
                            String.valueOf(transaction.getDate()),
                            String.valueOf(transaction.getStatus()),
                            transaction.getChangeId()
                    });
                    found = true;
                    break;
                }
            }
            writeToCsv(data, csvFilePath);
            return found;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * delete records from csv file using transactionId
     * @param transaction
     * @param csvFilePath
     * @return
     */
    public boolean deleteTransaction(Transaction transaction, String csvFilePath) {
        if(transaction == null || transaction.getTransactionId() == null){
            return false;
        }
        try{
            List<String[]> data = readFromCsv(csvFilePath);
            data.removeIf(row -> row[0].equals(transaction.getTransactionId()));
            writeToCsv(data, csvFilePath);
            return true;
        }catch (CsvException | IOException e){
            logger.error(e.getMessage());
            return false;
        }
    }

    public List<SkillOut> readProfInfBySkillNameFromCsv(String skillPart, String csvFilePath) throws IOException, CsvException {
        List<SkillOut> profInfList = new ArrayList<>();

        // Читаем данные из CSV
        List<String[]> data = readFromCsv(csvFilePath);

        for (String[] row : data) {
            // Предположим, что навык находится в соответствующем столбце (например, 5-й столбец)
            String skillName = row[5]; // Поменяйте индекс, если столбцы другие

            // Проверяем, содержит ли навык указанную часть названия
            if (skillName != null && skillName.toLowerCase().contains(skillPart.toLowerCase())) {
                SkillOut skillOut = new SkillOut();

                skillOut.setId(row[0]);
                skillOut.setSurname(row[1]);
                skillOut.setName(row[2]);
                skillOut.setPhoneNumber(row[3]);
                skillOut.setEmail(row[4]);
                skillOut.setSkillName(skillName);
                skillOut.setSkillDescription(row[6]); // Поменяйте индекс, если столбцы другие
                skillOut.setCost(Double.parseDouble(row[7])); // Поменяйте индекс, если столбцы другие
                skillOut.setPersDescription(row[8]); // Поменяйте индекс, если столбцы другие
                skillOut.setExp(Double.parseDouble(row[9])); // Поменяйте индекс, если столбцы другие
                skillOut.setRating(Double.parseDouble(row[10])); // Поменяйте индекс, если столбцы другие

                profInfList.add(skillOut);
            }
        }

        if (profInfList.isEmpty()) {
            throw new IOException("Can't find persons with skill name containing: " + skillPart);
        }

        return profInfList;
    }

    public void printProfInfListFromCsv(List<SkillOut> skillOutList) {
        if (skillOutList == null || skillOutList.isEmpty()) {
            System.out.println("Список профилей пуст.");
            return;
        }

        for (SkillOut skillOut : skillOutList) {
            System.out.println("ID: " + skillOut.getId());
            System.out.println("Фамилия: " + skillOut.getSurname());
            System.out.println("Имя: " + skillOut.getName());
            System.out.println("Номер телефона: " + skillOut.getPhoneNumber());
            System.out.println("Электронная почта: " + skillOut.getEmail());
            System.out.println("Навык: " + skillOut.getSkillName());
            System.out.println("Описание навыка: " + skillOut.getSkillDescription());
            System.out.println("Стоимость: " + skillOut.getCost());
            System.out.println("Описание пользователя: " + skillOut.getPersDescription());
            System.out.println("Опыт: " + skillOut.getExp());
            System.out.println("Рейтинг: " + skillOut.getRating());
            System.out.println("-----------------------------");
        }
    }

    public boolean insertRatingToCsv(String persId, Double rating, Double ratingBefore, String csvFilePath) throws IOException, CsvException {
        if (persId == null || rating == null || ratingBefore == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        Double finalRating;

        if (ratingBefore == 0.0) {
            finalRating = rating;
        } else {
            finalRating = (ratingBefore + rating) / 2.0;
        }

        // Читаем данные из CSV
        List<String[]> data = readFromCsv(csvFilePath);
        boolean updated = false;

        // Обновляем данные
        for (String[] row : data) {
            if (row[0].equals(persId)) {
                row[10] = String.valueOf(finalRating); // Предполагается, что рейтинг находится в 11-м столбце (индекс 10)
                updated = true;
                break;
            }
        }

        // Если запись не была найдена, возвращаем false
        if (!updated) {
            return false;
        }

        // Записываем обновлённые данные обратно в CSV
        writeToCsv(data, csvFilePath);
        return true;
    }
}
//TODO проверить как работает с csv