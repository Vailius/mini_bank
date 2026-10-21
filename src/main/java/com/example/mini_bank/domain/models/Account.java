package com.example.mini_bank.domain.models;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

import com.example.mini_bank.domain.exceptions.InsufficientFunds;

public class Account {
    private final UUID id;
    private final UUID userId;
    private final Instant createdAt;
    private final String number;
    private Money balance;

    private static String randomNumber() {
        return "ACC-"+UUID.randomUUID().toString().substring(0,8);
    }

    public Account(UUID userId, Currency currency) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        this.userId = userId;
        id = UUID.randomUUID();
        createdAt = Instant.now();
        number = randomNumber();
        balance = new Money(new BigDecimal("0.0"), currency);
    }

    public Account(UUID userId, Money balance) {
        this.userId = userId;
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.number = randomNumber();
        this.balance = Money.copy(balance);
    }

    public Account(Account other) {
        if (other == null) {
            throw new IllegalArgumentException("account for copy cannot be null");
        }
        userId = other.userId();
        id = other.id();
        createdAt = other.createdAt();
        number = other.number();
        balance = other.balance();
    }

    public UUID id() {
        return id;
    }

    public UUID userId() {
        return userId;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public String number() {
        return number;
    }

    public Money balance() {
        return new Money(balance.amount(), balance.currency());
    }

    public void debit(Money amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount for debit cannot be null");
        }
        if (balance.isLessThan(amount)) {
            throw new InsufficientFunds(balance, amount);
        }
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Amount cannot be negative for debit");
        }
        balance = balance.subtract(amount);
    }

    public void credit(Money amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount for credit cannot be null");
        }
        if (amount.isNegative()) {
            throw new IllegalArgumentException("amount to credit cannot be negative value");
        }
        balance = balance.add(amount);
    }
}