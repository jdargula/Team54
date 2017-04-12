package com.example.jayda.team54;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class EmailValidatorTest {
    private String arg;
    private static EmailValidator emailValidator;
    private boolean expectedValidation;


    public EmailValidatorTest(String str, boolean expectedValidation) {
        this.arg = str;
        this.expectedValidation = expectedValidation;
    }
    @BeforeClass
    public static void initialize() {
        emailValidator = new EmailValidator();
    }

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                { "user@gmail.com.234j",false },
                { "jd@j3@jd.com", false },
                { "asdf!!@mail.com", false },
                { "website@.com", false },
                { "asdfsa.com", false },
                { ".asf.asdf@asdf.com", false },
                { "asdf..asdf@asdf.com", false },


                { "asdf@gmail.com",true },
                { "asdf+asdf@gmail.com", true },
                { "abc.def-ghi@gmail-list.com", true },
                { "user1@example.com", true } };

        return Arrays.asList(data);
    }

    @Test
    public void testValidate()  {
        boolean res = emailValidator.validate(this.arg);
        String isValid = (res) ? "a valid" : "an invalid";
        System.out.println(arg+ " is " + isValid + " email.");
        assertEquals("Result", this.expectedValidation, res);
    }

}