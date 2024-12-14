package sfedu.xast;

import sfedu.xast.dao.PersInfDao;
import sfedu.xast.models.PersInf;

import java.sql.SQLException;

public class Main {
    static PersInfDao persInfDao;

    public static void main(String[] args) throws SQLException {
        PersInf persInf = new PersInf(1L, "Jackie Chan", "jackie@mail.ru");

        persInfDao.create(persInf);

        PersInf retrievedUser = persInfDao.read(persInf.getId());
        System.out.println(retrievedUser);
    }
}
