package com.stefanini.ordersystem.jdbc.impl;

import com.stefanini.ordersystem.jdbc.JdbcConnection;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Component
public class JdbcConnectionImpl implements JdbcConnection {

    private final Environment environment;

    public JdbcConnectionImpl(Environment environment) {
        this.environment = environment;
    }


    @Override
    public Connection getConnection() throws SQLException {
        String url = environment.getProperty("mysql-database-url");
        String user = environment.getProperty("mysql-user");
        String password = environment.getProperty("mysql-password");

        return DriverManager.getConnection(url, user, password);
    }
}
