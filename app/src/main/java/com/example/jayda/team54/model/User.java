package com.example.jayda.team54.model;

/**
 * Created by Alex on 2/16/2017.
 *
 * This is the general User class that each user will be part of
 *
 * We might want to make sub-classes for each user type in the future, especially for the different
 * permissions given to each user type
 */

public class User {
    private UserType type;
    private String emailAddress;

    public User(String emailAddress, UserType type) {
        this.emailAddress = emailAddress;
        this.type = type;
    }

    //Getter and Setter for UserType enum
    public void setType(UserType type) {
        this.type = type;
    }
    public UserType getType() {
        return type;
    }

    //Getter and Setter for user email address
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getEmailAddress() {
        return emailAddress;
    }

    //You can use this for testing or if we ever need a function that prints user information
    @Override
    public String toString() {
        return "Email: " + emailAddress + " User Type: " + type.getType();
    }
}
