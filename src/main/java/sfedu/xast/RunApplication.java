package sfedu.xast;

import sfedu.xast.api.DataProviderPSQL;
import sfedu.xast.models.PersInf;
import sfedu.xast.models.ProfInf;
import sfedu.xast.utils.ConfigurationUtil;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class RunApplication {

    public static void main(String[] args) throws SQLException, IOException {

        boolean flag = true;

        DataProviderPSQL dataProviderPSQL;
        Connection connection = DataProviderPSQL.getConnection();
        dataProviderPSQL = new DataProviderPSQL();

        Scanner sc = new Scanner(System.in);

        System.out.println("Привет! Я, консольный клиент приложения SkillSwap(⌒‿⌒)\n" +
                "Сперва давай пройдем регистрацию! \n" +
                "Тебе нужно будет ввести личные данные(ｏ・_・)ノ\n" +
                "Фамилия:");
        String surname = sc.nextLine();
        System.out.println("Имя:");
        String name = sc.nextLine();
        System.out.println("Номер телефона в формате 8(xxx)xxx-xx-xx:");
        String phoneNumber = sc.nextLine();
        System.out.println("Электронная почта:");
        String email = sc.nextLine();
        PersInf persInf = new PersInf(surname, name, phoneNumber, email);
        if(dataProviderPSQL.createPersInf(persInf)){
            System.out.println("Рад знакомству, " + persInf.getName() + " ( ˘⌣˘)♡(˘⌣˘ )");
        };


        while(flag) {
            System.out.println("Давай определимся, что ты хочешь (ｏ・_・)ノ\n" +
                    "1. Найти услугу\n" +
                    "2. Разместить услугу\n" +
                    "Выбери нужный номер!");

            int numChoice = sc.nextInt();
            sc.nextLine();
            switch (numChoice) {
                case 1:
                    System.out.println("Отлично, ты хочешь найти услугу (⌒‿⌒)\n" +
                            "Введи ее название, а я попробую найти: ");
                    String skillFind = sc.nextLine();
                    System.out.println("Вот, что мне удалось найти:");
                    dataProviderPSQL.printProfInfList(dataProviderPSQL.readProfInfBySkillName(skillFind));
                    flag = false;
                    break;
                case 2:
                    System.out.println("Отлично, ты хочешь разместить услугу (⌒‿⌒)\n" +
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
                    if(dataProviderPSQL.createProfInf(profInf, persInf)){
                        System.out.println("Отлично! Все данные записаны (⌒‿⌒)\n" +
                                "Ожидайте, с вами буду связываться заинтересованные люди.");
                    }
                    flag = false;
                    break;
                default:
                    System.out.println("Ой, такого номера нет, попробуй снова (」°ロ°)」");
            }
        }
    }
}
