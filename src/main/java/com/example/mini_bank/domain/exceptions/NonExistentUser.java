package com.example.mini_bank.domain.exceptions;

import java.util.UUID;

public class NonExistentUser extends RuntimeException {
    public NonExistentUser(String email) {
        super("Non existent user with email: " + email);
    }

    public NonExistentUser(UUID userId) {
        super("Non existent user with id " + userId);
    }
}