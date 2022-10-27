package com.stefanini.ordersystem.jdbc;

import com.stefanini.ordersystem.util.PropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcFactory {

    private final PropertiesReader propertiesReader = new PropertiesReader("application.properties");
    private static JdbcFactory connectionFactory = null;

    public Connection getConnection() throws SQLException {
        String url = propertiesReader.readProperty("mysql-database-url");
        String user = propertiesReader.readProperty("mysql-user");
        String password = propertiesReader.readProperty("mysql-password");

        Connection connection = null;
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public static JdbcFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new JdbcFactory();
        }
        return connectionFactory;
    }
}
