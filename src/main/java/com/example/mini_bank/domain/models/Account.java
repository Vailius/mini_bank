package com.example.mini_bank.domain.models;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

import com.example.mini_bank.domain.exceptions.InsufficientFunds;
import com.example.mini_bank.domain.exceptions.NullHelper;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class Account {
    private UUID id;
    private UUID userId;
    private Instant createdAt;
    private String number;
    @Embedded
    private Money balance;

    private static String randomNumber() {
        return "ACC-"+UUID.randomUUID().toString().substring(0,8);
    }

    public Account(UUID userId, Currency currency) {
        NullHelper.throwIfNull(userId, "userId");
        NullHelper.throwIfNull(currency, "currency");
        this.userId = userId;
        id = UUID.randomUUID();
        createdAt = Instant.now();
        number = randomNumber();
        balance = new Money(new BigDecimal("0.0"), currency);
    }

    public Account(UUID userId, Money balance) {
        NullHelper.throwIfNull(userId, "userId");
        NullHelper.throwIfNull(balance, "balance");
        this.userId = userId;
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.number = randomNumber();
        this.balance = Money.copy(balance);
    }

    public Account(Account other) {
        NullHelper.throwIfNull(other, "other");
        userId = other.userId();
        id = other.id();
        createdAt = other.createdAt();
        number = other.number();
        balance = other.balance();
    }

    protected Account() { }

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
        NullHelper.throwIfNull(amount, "amount");
        if (balance.isLessThan(amount)) {
            throw new InsufficientFunds(balance, amount);
        }
        if (amount.isNegative()) {
            throw new IllegalArgumentException("Amount cannot be negative for debit");
        }
        balance = balance.subtract(amount);
    }

    public void credit(Money amount) {
        NullHelper.throwIfNull(amount, "amount");
        if (amount.isNegative()) {
            throw new IllegalArgumentException("amount to credit cannot be negative value");
        }
        balance = balance.add(amount);
    }
}