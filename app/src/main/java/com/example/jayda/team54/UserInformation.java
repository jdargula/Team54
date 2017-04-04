package com.example.jayda.team54;

/**
 * User Information class.
 */

@SuppressWarnings("FieldCanBeLocal")
class UserInformation {

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
        this.name = n;
        this.address = a;
    }
    private final String name;
    private final String address;
}
