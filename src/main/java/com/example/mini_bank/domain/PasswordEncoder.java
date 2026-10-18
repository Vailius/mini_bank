package com.example.mini_bank.domain;

public interface PasswordEncoder {
    boolean matches(String rawPassword, String encodedPassword);
    String encode(String password);
}