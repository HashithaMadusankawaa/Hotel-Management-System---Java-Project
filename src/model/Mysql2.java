package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Mysql2 {

    private static Connection connection;

    public static void createConnection() throws Exception {

        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelairdb", "root", "Ha@12345678");
        }

    }

    public static ResultSet msearch(String query) throws Exception {

        createConnection();

        return connection.createStatement().executeQuery(query);

    }

    public static Integer miud(String query) throws Exception {

        createConnection();

        return connection.createStatement().executeUpdate(query);

    }
}
