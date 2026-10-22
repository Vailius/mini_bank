package com.example.mini_bank.domain.exceptions;

import java.util.UUID;

public class AccountNotEmpty extends RuntimeException {
    public AccountNotEmpty(UUID accountId) {
        super("Operation not allowed, account must be empty: " + accountId);
    }
}