package com.example.mini_bank.domain.models;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.example.mini_bank.domain.exceptions.IncompatibleCurrencies;
import com.example.mini_bank.domain.exceptions.InsufficientFunds;

public class AccountTest {

    private final UUID id = UUID.randomUUID();
    private final Currency currency = Currency.getInstance("USD");

    Account newAccount() {
        return new Account(id, currency);
    }

    Money negativeMoney() {
        return new Money(new BigDecimal("-5.0"), currency);
    }

    Money moneyRub(String amount) {
        return new Money(new BigDecimal(amount), Currency.getInstance("RUB"));
    }

    Money moneyUsd(String amount) {
        return new Money(new BigDecimal(amount), currency);
    }
    
    @Test
    void test_create_with_null_currency() {
        Currency cur = null;
        assertThrows(IllegalArgumentException.class, () -> new Account(id, cur));
    }

    @Test
    void test_create_with_null_userId() {
        assertThrows(IllegalArgumentException.class, () -> new Account(null, currency));
    }

    @Test
    void test_debit_with_null() {
        Account account = newAccount();
        assertThrows(IllegalArgumentException.class, () -> account.debit(null));
    }

    @Test
    void test_credit() {
        Account account = newAccount();
        account.credit(moneyUsd("10.0"));
        assertTrue(account.balance().equals(moneyUsd("10.0")));
    }

    @Test
    void test_debit() {
        Account account = newAccount();
        account.credit(moneyUsd("10.0"));
        account.debit(moneyUsd("5.0"));
        assertTrue(account.balance().equals(moneyUsd("5.0")));
    }

    @Test
    void test_debit_greater_of_balance() {
        Account account = newAccount();
        account.credit(moneyUsd("10.0"));
        assertThrows(InsufficientFunds.class, () -> account.debit(moneyUsd("11.0")));
    }

    @Test
    void test_credit_with_different_currency() {
        Account account = newAccount();
        assertThrows(IncompatibleCurrencies.class, () -> account.credit(moneyRub("5.0")));
    }

    @Test
    void test_debit_with_different_currency() {
        Account account = newAccount();
        account.credit(moneyUsd("10.0"));
        assertThrows(IncompatibleCurrencies.class, () -> account.debit(moneyRub("5.0")));
    }

    @Test
    void test_credit_with_null() {
        Account account = newAccount();
        assertThrows(IllegalArgumentException.class, () -> account.credit(null));
    }

    @Test
    void test_credit_with_negative() {
        Account account = newAccount();
        assertThrows(IllegalArgumentException.class, () -> account.credit(negativeMoney()));
    }

    @Test
    void test_debit_with_negative() {
        Account account = newAccount();
        assertThrows(IllegalArgumentException.class, () -> account.debit(negativeMoney()));
    }
}