package com.example.mini_bank.domain.exceptions;

public class NullHelper {
    public static void throwIfNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(name + " cannot be null");
        }
    }
}