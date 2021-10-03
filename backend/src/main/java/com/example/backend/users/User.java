package com.example.backend.users;

public class User {
    public String username;
    public String dateOfBirth;

    public User() {}

    public User(
            String username,
            String dateOfBirth) {
        this.username = username;
        this.dateOfBirth = dateOfBirth;
    }

    /* General Information */
    public String getUsername() {
        return username;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /* Steps */
    public int countStepsOfTheDay() {
        //TODO: need implemment
        return 10;
    }
}


