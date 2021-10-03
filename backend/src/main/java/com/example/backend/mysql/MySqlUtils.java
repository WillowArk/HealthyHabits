package com.example.backend.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlUtils {
    Connection conn;

    public MySqlUtils() throws SQLException {
        conn = DriverManager.getConnection(MySqlHandler.DB_URL, MySqlHandler.USER, MySqlHandler.PASS);
    }

    public MySqlUtils(Connection conn) {
        this.conn = conn;
    }

    public boolean executeUpdate(String sqlUpdate) {
        try(Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sqlUpdate);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet executeQuery(String sqlQuery) {
        try(Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int countRows(String table_name) {
        try(Statement stmt = conn.createStatement()) {
            String sqlQuery = "SELECT * FROM " + table_name;
            ResultSet rs = stmt.executeQuery(sqlQuery);
            int nRows = 0;
            while (rs.next()) {
                nRows++;
            }
            return nRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}

