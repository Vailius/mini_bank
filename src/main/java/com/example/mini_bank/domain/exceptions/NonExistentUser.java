package com.example.mini_bank.domain.exceptions;

public class NonExistentUser extends RuntimeException {
    public NonExistentUser(String email) {
        super("Non existent user with email: " + email);
    }
}