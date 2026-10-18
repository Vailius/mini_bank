package com.example.mini_bank.domain;

public class PasswordEncoderMock implements PasswordEncoder {
    private int encodeInvokes = 0;
    private int matchInvokes = 0;

    private String hash(String password) {
        return "ENC-"+password;
    }

    @Override
    public String encode(String password) {
        encodeInvokes++;
        return hash(password);
    }

    @Override
    public boolean matches(String raw, String encoded) {
        matchInvokes++;
        return encoded.equals(hash(raw));
    }

    public void clear() {
        encodeInvokes = 0;
        matchInvokes = 0;
    }

    public int encodeInvokes() {
        return encodeInvokes;
    }

    public int matchInvokes() {
        return matchInvokes;
    }
}