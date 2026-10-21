package com.example.mini_bank.domain.exceptions;
import java.util.UUID;

public class SameAccountTransfer extends RuntimeException {
    public SameAccountTransfer(UUID accountId) {
        super("Same account transfer: " + accountId);
    }
}