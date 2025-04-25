package sfedu.xast;

import com.opencsv.exceptions.CsvException;
import org.xml.sax.SAXException;
//import sfedu.xast.api.*;
import sfedu.xast.models.*;
import sfedu.xast.utils.Status;

import javax.management.modelmbean.XMLParseException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

//TODO не отрабатывает только xml и csv

public class RunApplication {

    /*public static void main(String[] args) throws SQLException, IOException, CsvException, ParserConfigurationException, XMLParseException, SAXException {

        String sourceFile = " ";
        boolean flag = true;

        DataProviderPSQL dataProviderPSQL;
        DataProviderXml dataProviderXml = new DataProviderXml();
        DataProviderMongo dataProviderMongo = new DataProviderMongo("test");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        Connection connection = DataProviderPSQL.getConnection();
        dataProviderPSQL = new DataProviderPSQL();

        Scanner sc = new Scanner(System.in);
        System.out.println("Привет! Я, консольный клиент приложения SkillSwap.\n" +
                "Давай определимся с форматом сохранения данных. \n"+
                "Выбери нужную цифру: \n" +
                "1. CSV-файл\n"+
                "2. XML-файл\n"+
                "3. PostgreSQL\n"+
                "4. MongoDB");
        Integer number = sc.nextInt();
        sc.nextLine();
        switch (number) {
            case 1:
                sourceFile = "CSV";
                break;
            case 2:
                sourceFile = "XML";
                break;
            case 3:
                sourceFile = "PostgreSQL";
                break;
            case 4:
                sourceFile = "MongoDB";
                break;
        }

        System.out.println("Давай пройдем регистрацию! \n" +
                "Тебе нужно будет ввести личные данные.\n" +
                "Фамилия:");
        String surname = sc.nextLine();
        System.out.println("Имя:");
        String name = sc.nextLine();
        System.out.println("Номер телефона в формате 8(xxx)xxx-xx-xx:");
        String phoneNumber = sc.nextLine();
        System.out.println("Электронная почта:");
        String email = sc.nextLine();
        PersInf persInf = new PersInf(surname, name, phoneNumber, email);

        switch (sourceFile){
            case "CSV":
                dataProviderCsv.createPersInf(persInf);
                break;
            case "XML":
                dataProviderXml.createPersInf(persInf);
                break;
            case "PostgreSQL":
                dataProviderPSQL.createPersInf(persInf);
                break;
            case "MongoDB":
                dataProviderMongo.createPersInf(persInf);
                break;
        }

        System.out.println("Рад знакомству, " + persInf.getName());

        while(flag) {
            System.out.println("Давай определимся, что ты хочешь:\n" +
                    "1. Найти услугу\n" +
                    "2. Разместить услугу\n" +
                    "Выбери нужный номер!");

            int numChoice = sc.nextInt();
            sc.nextLine();
            switch (numChoice) {
                case 1:
                    System.out.println("Отлично, ты хочешь найти услугу!\n" +
                            "Введи ее название, а я попробую найти: ");
                    String skillFind = sc.nextLine();
                    System.out.println("Вот, что мне удалось найти:");

                    switch (sourceFile){
                        case "CSV":
                            dataProviderCsv.printProfInfList(dataProviderCsv.readProfInfBySkillName(skillFind));
                            break;
                        case "XML":
                            dataProviderXml.printProfInfList(dataProviderXml.readProfInfBySkillName(skillFind));
                            break;
                        case "PostgreSQL":
                            dataProviderPSQL.printProfInfList(dataProviderPSQL.readProfInfBySkillName(skillFind));
                            break;
                        case "MongoDB":
                            dataProviderMongo.printProfInfList(dataProviderMongo.readProfInfBySkillName(skillFind));
                            break;
                    }

                    System.out.println("Скопируй и вставь сюда ID пользователя, чей урок ты хочешь взять");
                    String neededId = sc.nextLine();

                    String skillNameForAns = "";
                    String retrievedId = "";
                    Double retrievedRating = 4.0;
                    switch (sourceFile){
                        case "CSV":
                            ProfInf retrievedProfInf = dataProviderCsv.readProfInfWithId(neededId);
                            skillNameForAns = retrievedProfInf.getSkillName();
                            retrievedId = retrievedProfInf.getPersId();
                            retrievedRating = retrievedProfInf.getRating();
                            break;
                        case "XML":
                            ProfInf retrievedProfInf1 = dataProviderXml.readProfInfWithId(neededId);
                            skillNameForAns = retrievedProfInf1.getSkillName();
                            retrievedId = retrievedProfInf1.getPersId();
                            retrievedRating = retrievedProfInf1.getRating();
                            break;
                        case "PostgreSQL":
                            ProfInf retrievedProfInf2 = dataProviderPSQL.readProfInfWithId(neededId);
                            skillNameForAns = retrievedProfInf2.getSkillName();
                            retrievedId = retrievedProfInf2.getPersId();
                            retrievedRating = retrievedProfInf2.getRating();
                            break;
                        case "MongoDB":
                            ProfInf retrievedProfInf3 = dataProviderMongo.readProfInfWithId(neededId);
                            skillNameForAns = retrievedProfInf3.getSkillName();
                            retrievedId = retrievedProfInf3.getPersId();
                            retrievedRating = retrievedProfInf3.getRating();
                            break;
                    }

                    System.out.println("Отлично. Информация о уроке по навыку <" + skillNameForAns + "> направлена пользователю.\n" +
                            "Ожидайте обратной связи!");
                    try {
                        Thread.sleep(6000); //тут они между собой связались, если все получилось, то даже и занятие провели
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    SkillExchange skillExchange = new SkillExchange(skillNameForAns, persInf.getId(), retrievedId);
                    switch (sourceFile){
                        case "CSV":
                            dataProviderCsv.createSkillExchange(skillExchange);
                            break;
                        case "XML":
                            dataProviderXml.createSkillExchange(skillExchange);
                            break;
                        case "PostgreSQL":
                            dataProviderPSQL.createSkillExchange(skillExchange);
                            break;
                        case "MongoDB":
                            dataProviderMongo.createSkillExchange(skillExchange);
                            break;
                    }

                    System.out.println("Получилось ли у вас провести занятие?\n" +
                            "Ответ дайте 1 - ДА или 2 - НЕТ, без учета регистра");
                    String answer = sc.nextLine();
                    if(answer.equalsIgnoreCase("1")){
                        Transaction transaction = new Transaction(Status.COMPLETED, skillExchange.getExchangeId());
                        switch (sourceFile){
                            case "CSV":
                                dataProviderCsv.createTransaction(transaction);
                                break;
                            case "XML":
                                dataProviderXml.createTransaction(transaction);
                                break;
                            case "PostgreSQL":
                                dataProviderPSQL.createTransaction(transaction);
                                break;
                            case "MongoDB":
                                dataProviderMongo.createTransaction(transaction);
                                break;
                        }
                        System.out.println("Оставьте небольшой отзыв");
                        System.out.println("Оценка урока от 1 до 5:");
                        Double rating = sc.nextDouble();
                        sc.nextLine();
                        System.out.println("Ваши впечатления: ");
                        String comment = sc.nextLine();
                        Review review = new Review(rating, comment, persInf.getId(), retrievedId);
                        switch (sourceFile){
                            case "CSV":
                                dataProviderCsv.createReview(review);
                                dataProviderCsv.insertRating(retrievedId, review.getRating(), retrievedRating);
                                break;
                            case "XML":
                                dataProviderXml.createReview(review);
                                dataProviderXml.insertRating(retrievedId, review.getRating(), retrievedRating);
                                break;
                            case "PostgreSQL":
                                dataProviderPSQL.createReview(review);
                                dataProviderPSQL.insertRating(retrievedId, review.getRating(), retrievedRating);
                                break;
                            case "MongoDB":
                                dataProviderMongo.createReview(review);
                                dataProviderMongo.insertRating(retrievedId, review.getRating(), retrievedRating);
                                break;
                        }
                        System.out.println("Нам очень приятно, что вы выбрали нашу платформу. Удачи!");
                        flag = false;
                    }else{
                        Transaction transaction = new Transaction(Status.CANCELED, skillExchange.getExchangeId());
                        switch (sourceFile){
                            case "CSV":
                                dataProviderCsv.createTransaction(transaction);
                                break;
                            case "XML":
                                dataProviderXml.createTransaction(transaction);
                                break;
                            case "PostgreSQL":
                                dataProviderPSQL.createTransaction(transaction);
                                break;
                            case "MongoDB":
                                dataProviderMongo.createTransaction(transaction);
                                break;
                        }
                        System.out.println("Нам очень жаль, что у вас не получилось связаться");
                        flag = false;
                    }
                    break;
                case 2:
                    System.out.println("Отлично, ты хочешь разместить услугу\n" +
                            "Давай заполним информацию о твоем навыке!\n" +
                            "Напиши его название:");
                    String skillName = sc.nextLine();
                    System.out.println("Дай краткое описание навыка");
                    String skillDescription = sc.nextLine();
                    System.out.println("Сколько ты хочешь получить за урок:");
                    Double cost = sc.nextDouble();
                    sc.nextLine();
                    System.out.println("Расскажи про свои профессиональные качества:");
                    String persDescription = sc.nextLine();
                    System.out.println("Напиши свой стаж работы в этой области:");
                    Double exp = sc.nextDouble();
                    ProfInf profInf = new ProfInf(persInf.getId(),skillName,skillDescription,cost,persDescription,exp,0.0);
                    switch (sourceFile){
                        case "CSV":
                            dataProviderCsv.createProfInf(profInf, persInf);
                            break;
                        case "XML":
                            dataProviderXml.createProfInf(profInf, persInf);
                            break;
                        case "PostgreSQL":
                            dataProviderPSQL.createProfInf(profInf, persInf);
                            break;
                        case "MongoDB":
                            dataProviderMongo.createProfInf(profInf, persInf);
                            break;
                    }
                    System.out.println("Отлично! Все данные записаны\n" +
                                "Ожидайте, с вами будут связываться заинтересованные люди.");
                    flag = false;
                    break;
                default:
                    System.out.println("Ой, такого номера нет, попробуй снова");
            }
        }
    }*/
}

