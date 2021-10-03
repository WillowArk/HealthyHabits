package com.example.backend.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class MySqlSleepUtils extends MySqlUtils {
    static final String TABLE_NAME = "HoursSleep";
    static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (ID int NOT NULL AUTO_INCREMENT, " +
            "ID_user int NOT NULL, date varchar(10) NOT NULL, hours int NOT NULL, " +
            "CONSTRAINT PK_Sleep PRIMARY KEY (ID), CONSTRAINT FK_Sleep FOREIGN KEY (ID_user) REFERENCES Users(ID) );";
    static final String INSERT_HOURS_SLEEP_TEMPLATE = "INSERT INTO " + TABLE_NAME + " (ID_user, date, hours) VALUES (%d, '%s', %d);";
    static final String QUERY_ALL_HOURS_SLEEP_TEMPLATE = "SELECT date, hours FROM " + TABLE_NAME + " WHERE ID_user=%d;";
    static final String QUERY_HOURS_SLEEP_PER_DAY_TEMPLATE = "SELECT hours FROM " + TABLE_NAME + " WHERE ID_user=%d AND date='%s';";

    public MySqlSleepUtils() throws SQLException {
        super();
    }

    public MySqlSleepUtils(Connection conn) {
        super(conn);
    }

    public boolean create_table() {
        return executeUpdate(CREATE_TABLE_SQL);
    }

    public boolean insertHoursSleep(int userId, String date, int hours) {
        String sql = String.format(INSERT_HOURS_SLEEP_TEMPLATE, userId, date, hours);
        return executeUpdate(sql);
    }

    public HashMap<String, Integer> queryAllHoursSleep(int userId) {
        try(Statement stmt = conn.createStatement()) {
            String query = String.format(QUERY_ALL_HOURS_SLEEP_TEMPLATE, userId);
            ResultSet rs = stmt.executeQuery(query);
            HashMap<String, Integer> allHoursSleep = new HashMap<String, Integer>();
            while (rs.next()) {
                allHoursSleep.put(rs.getString("date"), rs.getInt("hours"));
            }
            return allHoursSleep;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int queryHoursSleepOfDay(int userId, String date) {
        try(Statement stmt = conn.createStatement()) {
            String query = String.format(QUERY_HOURS_SLEEP_PER_DAY_TEMPLATE, userId, date);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("hours");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String args[]) throws SQLException {
        MySqlSleepUtils mySqlSleepUtils = new MySqlSleepUtils();
        String date = "08/07/2021";
        mySqlSleepUtils.insertHoursSleep(1, date, 7);

        HashMap<String, Integer> allHoursSleep = mySqlSleepUtils.queryAllHoursSleep(1);
        for(String date_1 : allHoursSleep.keySet()) {
            System.out.println(date_1 + ": " + allHoursSleep.get(date_1));
        }

        int hoursSleep = mySqlSleepUtils.queryHoursSleepOfDay(1, date);
        System.out.println(hoursSleep);
        System.out.println("Done");
    }
}

