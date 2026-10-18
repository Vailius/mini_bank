package com.example.mini_bank.domain.exceptions;

public class EmailAlreadyRegistered extends RuntimeException {
    public EmailAlreadyRegistered(String email) {
        super("Email already registered: " + email);
    }
}