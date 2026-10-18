package com.example.mini_bank.domain.exceptions;
import com.example.mini_bank.domain.models.Money;

public class InsufficientFunds extends RuntimeException {
    public InsufficientFunds(Money balance, Money debit) {
        super("Operation not allowed, insufficient funds: " + balance.amount() + " and debit: " + debit.amount());
    }
}