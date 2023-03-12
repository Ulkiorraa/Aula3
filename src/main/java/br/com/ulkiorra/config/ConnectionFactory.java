package br.com.ulkiorra.config;

import br.com.ulkiorra.util.Alerts;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    Alerts alerts = new Alerts();

    public static Connection getConnection() {
        try {
            Properties props = loadProperties();
            String url = props.getProperty("dburl");
            return DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("src/main/java/br/com/ulkiorra/config/db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
