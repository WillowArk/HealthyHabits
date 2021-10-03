package com.example.backend.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class MySqlGymUtils extends MySqlUtils {
    static final String TABLE_NAME = "MinutesInGym";
    static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (ID int NOT NULL AUTO_INCREMENT, " +
            "ID_user int NOT NULL, date varchar(10) NOT NULL, minutes int NOT NULL, " +
            "CONSTRAINT PK_Gym PRIMARY KEY (ID), CONSTRAINT FK_Gym FOREIGN KEY (ID_user) REFERENCES Users(ID) );";
    static final String INSERT_MINUTES_IN_GYM_TEMPLATE = "INSERT INTO " + TABLE_NAME + " (ID_user, date, minutes) VALUES (%d, '%s', %d);";
    static final String QUERY_ALL_MINUTES_IN_GYM_TEMPLATE = "SELECT date, minutes FROM " + TABLE_NAME + " WHERE ID_user=%d;";
    static final String QUERY_MINUTES_IN_GYM_PER_DAY_TEMPLATE = "SELECT minutes FROM " + TABLE_NAME + " WHERE ID_user=%d AND date='%s';";

    public MySqlGymUtils() throws SQLException {
        super();
    }

    public MySqlGymUtils(Connection conn) {
        super(conn);
    }

    public boolean create_table() {
        return executeUpdate(CREATE_TABLE_SQL);
    }

    public boolean insertMinutesInGym(int userId, String date, int minutes) {
        String sql = String.format(INSERT_MINUTES_IN_GYM_TEMPLATE, userId, date, minutes);
        return executeUpdate(sql);
    }

    public HashMap<String, Integer> queryAllMinutesInGym(int userId) {
        try(Statement stmt = conn.createStatement()) {
            String query = String.format(QUERY_ALL_MINUTES_IN_GYM_TEMPLATE, userId);
            ResultSet rs = stmt.executeQuery(query);
            HashMap<String, Integer> allMinutesInGym = new HashMap<String, Integer>();
            while (rs.next()) {
                allMinutesInGym.put(rs.getString("date"), rs.getInt("minutes"));
            }
            return allMinutesInGym;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int queryMinutesInGymOfDay(int userId, String date) {
        try(Statement stmt = conn.createStatement()) {
            String query = String.format(QUERY_MINUTES_IN_GYM_PER_DAY_TEMPLATE, userId, date);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("minutes");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String args[]) throws SQLException {
        MySqlGymUtils mySqlGymUtils = new MySqlGymUtils();
        String date = "08/07/2021";
        mySqlGymUtils.insertMinutesInGym(1, date, 20);

        HashMap<String, Integer> allMinutesInGym = mySqlGymUtils.queryAllMinutesInGym(1);
        for(String date_1 : allMinutesInGym.keySet()) {
            System.out.println(date_1 + ": " + allMinutesInGym.get(date_1));
        }

        int minutesInGymOfDay = mySqlGymUtils.queryMinutesInGymOfDay(1, date);
        System.out.println(minutesInGymOfDay);
        System.out.println("Done");
    }
}

