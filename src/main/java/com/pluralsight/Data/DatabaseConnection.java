package com.pluralsight.Data;

import java.sql.*;

public class DatabaseConnection {

    public static String username = "root";
    public static String password = "yearup";
    public static String dataBase = "sugarcreek";
    public static String URL = "jdbc:mysql://localhost:3306/" + dataBase;
    
    public static Connection getConnection () throws SQLException {
        Connection connection = DriverManager.getConnection(URL, username, password);
        return connection;
    }
}
