package com.example.backend.misc;

import com.example.backend.mysql.MySqlHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test1 {
    public static final String DB_URL = "jdbc:mysql://localhost/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection(DB_URL, MySqlHandler.USER, MySqlHandler.PASS);
            Statement stmt = conn.createStatement();
        ) {
            try {
                String sql = "CREATE DATABASE " + MySqlHandler.DB_NAME;
                stmt.executeUpdate(sql);
                System.out.println("Database dropped successfully...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
