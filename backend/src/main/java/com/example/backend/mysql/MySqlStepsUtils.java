package com.example.backend.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class MySqlStepsUtils extends MySqlUtils {
    static final String TABLE_NAME = "Steps";
    static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (ID int NOT NULL AUTO_INCREMENT, " +
            "ID_user int NOT NULL, date varchar(10) NOT NULL, nSteps int NOT NULL, " +
            "CONSTRAINT PK_Step PRIMARY KEY (ID), CONSTRAINT FK_Step FOREIGN KEY (ID_user) REFERENCES Users(ID) );";
    static final String INSERT_STEPS_TEMPLATE = "INSERT INTO " + TABLE_NAME + " (ID_user, date, nSteps) VALUES (%d, '%s', %d);";
    static final String QUERY_ALL_STEPS_TEMPLATE = "SELECT date, nSteps FROM " + TABLE_NAME + " WHERE ID_user=%d;";
    static final String QUERY_STEPS_PER_DAY_TEMPLATE = "SELECT nSteps FROM " + TABLE_NAME + " WHERE ID_user=%d AND date='%s';";

    public MySqlStepsUtils() throws SQLException {
        super();
    }

    public MySqlStepsUtils(Connection conn) {
        super(conn);
    }

    public boolean create_table() {
        return executeUpdate(CREATE_TABLE_SQL);
    }

    public boolean insertSteps(int userId, String date, int nSteps) {
        String sql = String.format(INSERT_STEPS_TEMPLATE, userId, date, nSteps);
        return executeUpdate(sql);
    }

    public HashMap<String, Integer> queryAllSteps(int userId) {
        try(Statement stmt = conn.createStatement()) {
            String query = String.format(QUERY_ALL_STEPS_TEMPLATE, userId);
            ResultSet rs = stmt.executeQuery(query);
            HashMap<String, Integer> allSteps = new HashMap<String, Integer>();
            while (rs.next()) {
                allSteps.put(rs.getString("date"), rs.getInt("nSteps"));
            }
            return allSteps;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int queryStepsOfDay(int userId, String date) {
        try(Statement stmt = conn.createStatement()) {
            String query = String.format(QUERY_STEPS_PER_DAY_TEMPLATE, userId, date);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("nSteps");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String args[]) throws SQLException {
        MySqlStepsUtils mySqlStepsUtils = new MySqlStepsUtils();
        String date = "08/07/2021";
        mySqlStepsUtils.insertSteps(1, date, 1000);

        HashMap<String, Integer> allSteps = mySqlStepsUtils.queryAllSteps(1);
        for(String date_1 : allSteps.keySet()) {
            System.out.println(date_1 + ": " + allSteps.get(date_1));
        }

        int steps = mySqlStepsUtils.queryStepsOfDay(1, date);
        System.out.println(steps);
        System.out.println("Done");
    }
}

