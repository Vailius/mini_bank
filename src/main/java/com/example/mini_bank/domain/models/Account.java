package com.example.mini_bank.domain.models;
import java.util.UUID;
import java.util.Instant;

public class Account {
    private final UUID id;
    private final UUID userId;
    private final Instant createdAt;
}