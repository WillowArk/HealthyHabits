package com.example.backend.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class MySqlFoodUtils extends MySqlUtils {
    static final String TABLE_NAME = "Food";
    static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (ID int NOT NULL AUTO_INCREMENT, " +
            "ID_user int NOT NULL, date varchar(10) NOT NULL, rate int NOT NULL, " +
            "CONSTRAINT PK_Food PRIMARY KEY (ID), CONSTRAINT FK_Food FOREIGN KEY (ID_user) REFERENCES Users(ID) );";
    static final String INSERT_FOOD_TEMPLATE = "INSERT INTO " + TABLE_NAME + " (ID_user, date, rate) VALUES (%d, '%s', %d);";
    static final String QUERY_ALL_FOOD_TEMPLATE = "SELECT date, rate FROM " + TABLE_NAME + " WHERE ID_user=%d;";
    static final String QUERY_FOOD_PER_DAY_TEMPLATE = "SELECT rate FROM " + TABLE_NAME + " WHERE ID_user=%d AND date='%s';";

    public MySqlFoodUtils() throws SQLException {
        super();
    }

    public MySqlFoodUtils(Connection conn) {
        super(conn);
    }

    public boolean create_table() {
        return executeUpdate(CREATE_TABLE_SQL);
    }

    public boolean insertFood(int userId, String date, int rate) {
        String sql = String.format(INSERT_FOOD_TEMPLATE, userId, date, rate);
        return executeUpdate(sql);
    }

    public HashMap<String, ArrayList<Integer>> queryAllFood(int userId) {
        try(Statement stmt = conn.createStatement()) {
            String query = String.format(QUERY_ALL_FOOD_TEMPLATE, userId);
            ResultSet rs = stmt.executeQuery(query);
            HashMap<String, ArrayList<Integer>> allFood = new HashMap<String, ArrayList<Integer>>();
            while (rs.next()) {
                String date = rs.getString("date");
                if (!allFood.containsKey(date)) allFood.put(date, new ArrayList<Integer>());
                allFood.get(date).add(rs.getInt("rate"));
            }
            return allFood;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Integer> queryFoodOfDay(int userId, String date) {
        try(Statement stmt = conn.createStatement()) {
            String query = String.format(QUERY_FOOD_PER_DAY_TEMPLATE, userId, date);
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Integer> foodOfDay = new ArrayList<Integer>();
            while (rs.next()) {
                foodOfDay.add(rs.getInt("rate"));
            }
            return foodOfDay;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String args[]) throws SQLException {
        MySqlFoodUtils mySqlFoodUtils = new MySqlFoodUtils();
        String date = "08/07/2021";
        mySqlFoodUtils.insertFood(1, date, 10);
        mySqlFoodUtils.insertFood(1, date, 1);
        mySqlFoodUtils.insertFood(1, date, 5);

        HashMap<String, ArrayList<Integer>> allFood = mySqlFoodUtils.queryAllFood(1);
        for(String date_1 : allFood.keySet()) {
            System.out.println(date_1 + ": " + allFood.get(date_1));
        }

        ArrayList<Integer> foodOfDay = mySqlFoodUtils.queryFoodOfDay(1, date);
        System.out.println(foodOfDay);
        System.out.println("Done");
    }
}
