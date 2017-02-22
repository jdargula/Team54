package com.example.jayda.team54;

/**
 * Created by jayda on 2/17/2017.
 */

public class UserInformation {

    /**
     * No args class constructor.
     */
    public UserInformation() {}

    /**
     * Class constructor with params for name and address.
     * @param n The user's name
     * @param a The user's home address
     */
    public UserInformation(String n, String a) {
        name = n;
        address = a;
    }

    public String name;
    public String address;

}
