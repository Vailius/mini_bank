package com.example.mini_bank.jpa.entities;

import com.example.mini_bank.domain.models.User;

import jakarta.persistence.Embedded;

public class UserEntity {
    private long id = 0;
    @Embedded
    private User user;

    public UserEntity(long id, User user) {
        this.id = id;
        this.user = user;
    }
    
    public UserEntity(UserEntity other) {
        id = other.id();
        user = other.user();
    }

    protected UserEntity() { }

    public long id() {
        return id;
    }

    public User user() {
        return new User(user);
    }
}