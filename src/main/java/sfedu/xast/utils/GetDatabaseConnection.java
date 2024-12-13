package sfedu.xast.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class GetDatabaseConnection {
    private static Connection connection;
    public static Connection getConnection() throws SQLException, IOException {
        if (connection == null) {
            Properties props = new Properties();
            try(InputStream input = GetDatabaseConnection.class.getClassLoader().
                    getResourceAsStream("src/main/resources/enviroment.properties")) {
                props.load(input);
            }
            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
