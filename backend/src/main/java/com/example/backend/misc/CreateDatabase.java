package com.example.backend.misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;

import com.example.backend.mysql.MySqlHandler;
import com.example.backend.users.User1;
import com.example.backend.users.Utils;

public class CreateDatabase {
    public static final String DB_URL = String.format(
            "jdbc:mysql://%s/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
            MySqlHandler.SERVER_IP);
    static MySqlHandler mySqlHandler;

    public static void cleanUpDB() throws SQLException {
        try(Connection conn = DriverManager.getConnection(DB_URL, MySqlHandler.USER, MySqlHandler.PASS);
            Statement stmt = conn.createStatement();
        ) {
            try {
                String sql = "DROP DATABASE " + MySqlHandler.DB_NAME;
                stmt.executeUpdate(sql);
                System.out.println("Database dropped successfully...");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createNewDB() throws SQLException {
        try(Connection conn = DriverManager.getConnection(DB_URL, MySqlHandler.USER, MySqlHandler.PASS);
            Statement stmt = conn.createStatement();
        ) {
            String sql = "CREATE DATABASE " + MySqlHandler.DB_NAME;
            stmt.executeUpdate(sql);
            System.out.println("Database create successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createAllTables() throws SQLException {
        mySqlHandler = new MySqlHandler();
        mySqlHandler.create_user_table();
        mySqlHandler.create_steps_table();
        mySqlHandler.create_food_table();
        mySqlHandler.create_gym_table();
        mySqlHandler.create_sleep_table();
        System.out.println("Create All tables done");
    }

    public static User1[] loadUserFromJson(String filepath) throws Exception {
        Gson gson = new Gson();
        String jsonString = Utils.readFileAsString(filepath);
        User1[] user1s = gson.fromJson(jsonString, User1[].class);
        System.out.println("Load all users from json done");
        return user1s;
    }

    public static void insertOneUser(User1 user1) {
        mySqlHandler.insertUser(user1.username, user1.dateOfBirth);
        int userId = mySqlHandler.findUserId(user1.username);
        /* insert steps */
        for(String date: user1.steps.keySet()) {
            int nSteps = user1.steps.get(date);
            mySqlHandler.insertSteps(userId, date, nSteps);
        }

        /* Food */
        for(String date: user1.food.keySet()) {
            ArrayList<Integer> foodPerDay = user1.food.get(date);
            for(int rate: foodPerDay) {
                mySqlHandler.insertFood(userId, date, rate);
            }
        }

        /* insert gym */
        for(String date: user1.minutesInGym.keySet()) {
            int minutes = user1.minutesInGym.get(date);
            mySqlHandler.insertMinutesInGym(userId, date, minutes);
        }

        /* insert sleep */
        for(String date: user1.hoursSleep.keySet()) {
            int hours = user1.hoursSleep.get(date);
            mySqlHandler.insertHoursSleep(userId, date, hours);
        }

        System.out.println("Insert user " + user1.username + " done");
    }

    public static void main(String[] args) throws Exception {
        cleanUpDB();
        createNewDB();
        createAllTables();
        User1[] user1s = loadUserFromJson("data/a_1.json");
        for (User1 user1 : user1s) {
            insertOneUser(user1);
        }
    }

    public static void main1(String[] args) throws Exception {
        String str = Utils.readFileAsString("data/a.json");
        System.out.println(str);
    }
}

