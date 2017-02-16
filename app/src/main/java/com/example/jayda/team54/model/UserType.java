package com.example.jayda.team54.model;

/**
 * Created by Alex on 2/16/2017.
 */

public enum UserType {
    USER("User"),
    WORKER("Worker"),
    MANAGER("Manager"),
    ADMIN("Administrator");

    private final String type;

    UserType(String type)  {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
