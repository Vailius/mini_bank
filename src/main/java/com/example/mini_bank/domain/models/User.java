package com.example.mini_bank.domain.models;
import java.time.Instant;
import java.util.UUID;

import com.example.mini_bank.domain.exceptions.NullHelper;

import jakarta.persistence.Embeddable;

@Embeddable
public class User {
    private UUID id;
    private Instant createdAt;
    private String email;
    private String password;

    public User(String email, String password) {
        NullHelper.throwIfNull(email, "email");
        NullHelper.throwIfNull(password, "password");
        
        id = UUID.randomUUID();
        createdAt = Instant.now();

        this.email = email;
        this.password = password;
    }

    public User(User other) {
        id = other.id();
        createdAt = other.createdAt();
        email = other.email();
        password = other.password();
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