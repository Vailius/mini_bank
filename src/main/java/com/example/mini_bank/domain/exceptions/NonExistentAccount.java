package com.example.mini_bank.domain.exceptions;

import java.util.UUID;

public class NonExistentAccount extends RuntimeException {
    public NonExistentAccount(UUID accountId) {
        super("Non existent account: " + accountId);
    }
}