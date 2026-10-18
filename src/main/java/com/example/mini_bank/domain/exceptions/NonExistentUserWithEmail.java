package com.example.mini_bank.domain.exceptions;

public class NonExistentUserWithEmail extends RuntimeException {
    public NonExistentUserWithEmail(String email) {
        super("Non existent user with email: " + email);
    }
}