package com.example.jayda.team54;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationActivityTest {

    private String email;
    private String password;

    @Before
    public void setup() {
        email = "user@example.com";
        password = "password";
    }
    @Test
    public void testParamsNotNull() {
        assertFalse(email.isEmpty());
        assertFalse(password.isEmpty());
    }

    @Test
    public void testValidEmail() {

    }

}