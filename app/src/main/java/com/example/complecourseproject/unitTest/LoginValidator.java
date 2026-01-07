package com.example.complecourseproject.unitTest;

public class LoginValidator {
    public static boolean isValid(String email, String password) {
        return email.contains("@") && password.length() >= 6;
    }
}
