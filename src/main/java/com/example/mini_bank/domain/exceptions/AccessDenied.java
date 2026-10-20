package com.example.mini_bank.domain.exceptions;

import java.util.UUID;

public class AccessDenied extends RuntimeException {
    public AccessDenied(String reason) {
        super("Access denied: " + reason);
    }

    public static void throwAccessToSomeone(UUID accountId) {
        throw new AccessDenied("access to someone else's account: "+ accountId);
    }
}