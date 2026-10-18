package com.example.mini_bank.domain.models;
import com.example.mini_bank.domain.exceptions.InsufficientFunds;
import java.util.UUID;
import java.time.Instant;
import java.util.Currency;
import java.math.BigDecimal;

public class Account {
    private final UUID id;
    private final UUID userId;
    private final Instant createdAt;
    private final String number;
    private Money balance;

    public Account(UUID userId, Currency currency) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        this.userId = userId;
        id = UUID.randomUUID();
        createdAt = Instant.now();
        number = "ACC-" + UUID.randomUUID().toString().substring(0, 8);
        balance = new Money(new BigDecimal("0.0"), currency);
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