package com.example.mini_bank.domain.models;
import java.time.Instant;
import java.util.UUID;

import com.example.mini_bank.domain.exceptions.NullHelper;

public class User {
    private final UUID id;
    private final Instant createdAt;
    private final String email;
    private final String password;

    public User(String email, String password) {
        NullHelper.throwIfNull(email, "email");
        NullHelper.throwIfNull(password, "password");
        
        id = UUID.randomUUID();
        createdAt = Instant.now();

        this.email = email;
        this.password = password;
    }

    public UUID id() {
        return id;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public String email() {
        return email;
    }

    public String password() {
        return password;
    }
}