package com.example.jayda.team54;

import org.junit.Test;
import static org.junit.Assert.*;



/**
 * Made by Alex Dunford
 *
 * Junit created to test checkEmail and checkPassword methods of RegistrationActivity
 */

public class RegistrationActivityTest {

    private String email;
    private String password;


    /**
     * Null email check
     */

    @Test
    public void nullEmailTest() {
        email = "";
        assertFalse(RegistrationActivity.checkEmail(email));
    }

    /**
     * Invalid email 1st half
     */

    @Test
    public void firstHalfEmailTest() {
        email = "@gmail.com";
        assertFalse(RegistrationActivity.checkEmail(email));
    }

    /**
     * Invalid email 2nd half
     */

    @Test
    public void secondHalfEmailTest() {
        email = "test@com";
        assertFalse(RegistrationActivity.checkEmail(email));
    }

    /**
     * Invalid email without @ symbol
     */

    @Test
    public void emailAtTest() {
        email = "testgmail.com";
        assertFalse(RegistrationActivity.checkEmail(email));
    }

    /**
     * Valid email test
     */

    @Test
    public void validEmailTest() {
        email = "test@gmail.com";
        assertTrue(RegistrationActivity.checkEmail(email));
    }

    /**
     * Null Password Test
     */

    @Test
    public void passwordNullTest() {
        password = "";
        assertFalse(RegistrationActivity.checkPassword(password));
    }

    /**
     * Password Length Test
     */

    @Test
    public void passwordLengthTest() {
        password = "t";
        assertFalse(RegistrationActivity.checkPassword(password));
    }

    /**
     * Password contains space Test
     */

    @Test
    public void passwordSpaceTest() {
        password = "t est";
        assertFalse(RegistrationActivity.checkPassword(password));
    }

    /**
     * Valid password test
     */

    @Test
    public void validPasswordTest() {
        password = "test";
        assertTrue(RegistrationActivity.checkPassword(password));
    }
}