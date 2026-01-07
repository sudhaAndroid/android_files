package com.example.complecourseproject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.complecourseproject.unitTest.LoginValidator;

import org.junit.Test;

public class LoginValidatorTest {
    @Test
    public void validCredentials_returnsTrue() {
        assertTrue(LoginValidator.isValid(
                "test@gmail.com",
                "123456"
        ));
    }

    @Test
    public void invalidPassword_returnsFalse() {
        assertFalse(LoginValidator.isValid(
                "test@gmail.com",
                "123"
        ));
    }

    @Test
    public void invalidEmail_returnsFalse() {
        assertFalse(LoginValidator.isValid(
                "testgmail.com",
                "123456"
        ));
    }
}
