package com.example.backend.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.backend.users.User;


public class MySqlHandler {
    public static final String SERVER_IP = "35.204.158.58";
    public static final String DB_NAME = "Statefarm";
    public static final String DB_URL = String.format(
            "jdbc:mysql://%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
            SERVER_IP, DB_NAME);
    public static final String USER = "guest";
    public static final String PASS = "guest123";
    Connection conn;

    MySqlUserUtils mySqlUserUtils;
    MySqlStepsUtils mySqlStepsUtils;
    MySqlFoodUtils mySqlFoodUtils;
    MySqlGymUtils mySqlGymUtils;
    MySqlSleepUtils mySqlSleepUtils;

    public MySqlHandler() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        mySqlUserUtils = new MySqlUserUtils(conn);
        mySqlStepsUtils = new MySqlStepsUtils(conn);
        mySqlFoodUtils = new MySqlFoodUtils(conn);
        mySqlGymUtils = new MySqlGymUtils(conn);
        mySqlSleepUtils = new MySqlSleepUtils(conn);
        ;	}

    /* User */
    public boolean create_user_table() {
        return mySqlUserUtils.create_table();
    }

    public boolean insertUser(User user) {
        return mySqlUserUtils.insertUser(user);
    }

    public boolean insertUser(String username, String dateOfBirth) {
        return mySqlUserUtils.insertUser(username, dateOfBirth);
    }

    public int findUserId(String username) {
        return mySqlUserUtils.findUserId(username);
    }

    public int countUsers() {
        return mySqlUserUtils.countUsers();
    }

    /* Steps */
    public boolean create_steps_table() {
        return mySqlStepsUtils.create_table();
    }

    public boolean insertSteps(int userId, String date, int nSteps) {
        return mySqlStepsUtils.insertSteps(userId, date, nSteps);
    }

    public HashMap<String, Integer> queryAllSteps(int userId) {
        return mySqlStepsUtils.queryAllSteps(userId);
    }

    public int queryStepsOfDay(int userId, String date) {
        return mySqlStepsUtils.queryStepsOfDay(userId, date);
    }

    /* Food */
    public boolean create_food_table() {
        return mySqlFoodUtils.create_table();
    }

    public boolean insertFood(int userId, String date, int rate) {
        return mySqlFoodUtils.insertFood(userId, date, rate);
    }

    public HashMap<String, ArrayList<Integer>> queryAllFood(int userId) {
        return mySqlFoodUtils.queryAllFood(userId);
    }

    public ArrayList<Integer> queryFoodOfDay(int userId, String date) {
        return mySqlFoodUtils.queryFoodOfDay(userId, date);
    }

    /* MinutesInGym */
    public boolean create_gym_table() {
        return mySqlGymUtils.create_table();
    }

    public boolean insertMinutesInGym(int userId, String date, int minutes) {
        return mySqlGymUtils.insertMinutesInGym(userId, date, minutes);
    }

    public HashMap<String, Integer> queryAllMinutesInGym(int userId) {
        return mySqlGymUtils.queryAllMinutesInGym(userId);
    }

    public int queryMinutesInGymOfDay(int userId, String date) {
        return mySqlGymUtils.queryMinutesInGymOfDay(userId, date);
    }

    /* HoursSleep */
    public boolean create_sleep_table() {
        return mySqlSleepUtils.create_table();
    }

    public boolean insertHoursSleep(int userId, String date, int hours) {
        return mySqlSleepUtils.insertHoursSleep(userId, date, hours);
    }

    public HashMap<String, Integer> queryAllHoursSleep(int userId) {
        return mySqlSleepUtils.queryAllHoursSleep(userId);
    }

    public int queryHoursSleepOfDay(int userId, String date) {
        return mySqlSleepUtils.queryHoursSleepOfDay(userId, date);
    }

    /* Test database */
    public static void main(String[] args) throws SQLException {
        MySqlHandler mySqlHandler = new MySqlHandler();

        /* Users */
        String dateOfBirth = "01/01/2001";
        int nUsers = mySqlHandler.countUsers();
        String new_username = "user" + String.valueOf(nUsers + 1);
        mySqlHandler.insertUser(new_username, dateOfBirth);
        int user_id = mySqlHandler.findUserId(new_username);
        System.out.println("user_id = " + user_id);
        System.out.println("Done Users");
        System.out.println("===========================================");

        String date = "08/07/2021";

        /* Steps */
        mySqlHandler.insertSteps(1, date, 1000);
        HashMap<String, Integer> allSteps = mySqlHandler.queryAllSteps(1);
        for(String date_1 : allSteps.keySet()) {
            System.out.println(date_1 + ": " + allSteps.get(date_1));
        }
        int steps = mySqlHandler.queryStepsOfDay(1, date);
        System.out.println(steps);
        System.out.println("Done Steps");
        System.out.println("===========================================");

        /* Food */
        mySqlHandler.insertFood(1, date, 10);
        mySqlHandler.insertFood(1, date, 1);
        mySqlHandler.insertFood(1, date, 5);

        HashMap<String, ArrayList<Integer>> allFood = mySqlHandler.queryAllFood(1);
        for(String date_1 : allFood.keySet()) {
            System.out.println(date_1 + ": " + allFood.get(date_1));
        }

        ArrayList<Integer> foodOfDay = mySqlHandler.queryFoodOfDay(1, date);
        System.out.println(foodOfDay);
        System.out.println("Done Food");
        System.out.println("===========================================");

        /* Gym */
        mySqlHandler.insertMinutesInGym(1, date, 20);
        HashMap<String, Integer> allMinutesInGym = mySqlHandler.queryAllMinutesInGym(1);
        for(String date_1 : allMinutesInGym.keySet()) {
            System.out.println(date_1 + ": " + allMinutesInGym.get(date_1));
        }
        int minutesInGymOfDay = mySqlHandler.queryMinutesInGymOfDay(1, date);
        System.out.println(minutesInGymOfDay);
        System.out.println("Done Gym");
        System.out.println("===========================================");

        /* Sleep */
        mySqlHandler.insertHoursSleep(1, date, 7);
        HashMap<String, Integer> allHoursSleep = mySqlHandler.queryAllHoursSleep(1);
        for(String date_1 : allHoursSleep.keySet()) {
            System.out.println(date_1 + ": " + allHoursSleep.get(date_1));
        }
        int hoursSleep = mySqlHandler.queryHoursSleepOfDay(1, date);
        System.out.println(hoursSleep);
        System.out.println("Done Sleep");
        System.out.println("===========================================");
    }
}

