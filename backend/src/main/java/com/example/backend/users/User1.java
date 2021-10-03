package com.example.backend.users;

import java.util.ArrayList;
import java.util.HashMap;

public class User1 {
    public String username;
    public String password;
    public String dateOfBirth;
    public HashMap<String, Integer> steps = new HashMap<>();
    public HashMap<String, ArrayList<Integer>> food = new HashMap<>();
    public HashMap<String, Integer> minutesInGym = new HashMap<>();
    public HashMap<String, Integer> minutesMeditation = new HashMap<>();
    public HashMap<String, Integer> hoursSleep = new HashMap<>();
    public ArrayList<String> medicalHistoryFilePaths;

    public User1() {}

    public User1(
            String username,
            String password,
            String dateOfBirth) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    /* General Information */
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /* Steps */
    public int countStepsOfTheDay() {
        //TODO: need implemment
        return 10;
    }

    public void setStepsOfTheDay(String date, int numSteps) {
        //TODO: upload to a database
        steps.put(date, numSteps);
    }

    public int getStepsOfTheDay(String date) {
        //TODO: retrieve from a database
        if (steps.containsKey(date))
            return steps.get(date);
        return 0;
    }

}

