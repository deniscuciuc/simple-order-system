package com.stefanini.ordersystem.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface JdbcConnection {


    Connection getConnection() throws SQLException;
}
