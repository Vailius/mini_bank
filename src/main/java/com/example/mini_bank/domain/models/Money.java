package com.example.mini_bank.domain.models;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import com.example.mini_bank.domain.exceptions.IncompatibleCurrencies;
import com.example.mini_bank.domain.exceptions.NullHelper;

import jakarta.persistence.Embeddable;

@Embeddable
public record Money(BigDecimal amount, Currency currency) {

    public Money {
        NullHelper.throwIfNull(amount, "amount");
        NullHelper.throwIfNull(currency, "currency");
    }

    public static Money copy(Money m) {
        NullHelper.throwIfNull(m, "m");
        return new Money(m.amount(), m.currency());
    }

    public Money add(Money other) {
        NullHelper.throwIfNull(other, "other");
        IncompatibleCurrencies.throwIfIncompatible(currency, other.currency());
        return new Money(amount.add(other.amount()), currency);
    }

    public Money subtract(Money other) {
        NullHelper.throwIfNull(other, "other");
        IncompatibleCurrencies.throwIfIncompatible(currency, other.currency());
        return new Money(amount.subtract(other.amount()), currency);
    }

    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isLessThan(Money other) {
        return amount.compareTo(other.amount()) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money other)) return false;
        return currency.equals(other.currency)
                && amount.compareTo(other.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount.stripTrailingZeros());
    }
}