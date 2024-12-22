package sfedu.xast;

import sfedu.xast.api.DataProviderPSQL;
import sfedu.xast.models.PersInf;

import java.util.Scanner;

public class RunApplication {

    public static void main(String[] args) {

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
        DataProviderPSQL dataProviderPSQL = new DataProviderPSQL();
        dataProviderPSQL.createPersInf(persInf);


        while(true) {
            System.out.println("Давай определимся, что ты хочешь (ｏ・_・)ノ\n" +
                    "1. Найти услугу\n" +
                    "2. Разместить услугу\n" +
                    "Выбери нужный номер!");

            int numChoice = sc.nextInt();
            switch (numChoice) {
                case 1:
                    System.out.println("Отлично, ты хочешь найти услугу(⌒‿⌒)\n" +
                            "Сначала тебе нужно зарегистрироваться\n"+
                            "Давай знакомится!");
                    break;
                case 2:
                    System.out.println("Отлично, ты хочешь разместить услугу(⌒‿⌒)\n" +
                            "Сначала тебе нужно зарегистрироваться");
                    break;
                default:
                    System.out.println("Ой, такого номера нет, попробуй снова (х_х)");
            }
        }
    }
}
