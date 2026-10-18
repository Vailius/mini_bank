package com.example.mini_bank.domain.exceptions;
import java.util.Currency;

public class IncompatibleCurrencies extends RuntimeException {
    public IncompatibleCurrencies(Currency a, Currency b) {
        super("Incompatible currencies operations not allowed : " + a + " with " + b);
    }

    public static void throwIfIncompatible(Currency a, Currency b) {
        if (!a.equals(b)) {
            throw new IncompatibleCurrencies(a, b);
        }
    }
}