package com.example.mini_bank.domain.models;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Currency;
import static org.junit.jupiter.api.Assertions.*;
import com.example.mini_bank.domain.exceptions.IncompatibleCurrencies;

class MoneyTest {

    private final Currency RUB = Currency.getInstance("RUB");
    private final Currency USD = Currency.getInstance("USD");

    @Test
    void should_add_two_amounts_of_same_currency() {
        Money a = new Money(new BigDecimal("100"), RUB);
        Money b = new Money(new BigDecimal("50"), RUB);

        Money result = a.add(b);

        assertEquals(0, result.amount().compareTo(new BigDecimal("150")));
        assertEquals(RUB, result.currency());
    }

    @Test
    void should_subtract_amounts() {
        Money a = new Money(new BigDecimal("100"), RUB);
        Money b = new Money(new BigDecimal("40"), RUB);

        Money result = a.subtract(b);

        assertEquals(0, result.amount().compareTo(new BigDecimal("60")));
    }

    @Test
    void should_allow_negative_result_of_subtract() {
        Money a = new Money(new BigDecimal("50"), RUB);
        Money b = new Money(new BigDecimal("100"), RUB);

        Money result = a.subtract(b);

        assertTrue(result.isNegative());
        assertEquals(0, result.amount().compareTo(new BigDecimal("-50")));
    }

    @Test
    void should_throw_when_adding_different_currencies() {
        Money rub = new Money(new BigDecimal("100"), RUB);
        Money usd = new Money(new BigDecimal("100"), USD);

        assertThrows(IncompatibleCurrencies.class, () -> rub.add(usd));
    }

    @Test
    void should_throw_when_subtracting_different_currencies() {
        Money rub = new Money(new BigDecimal("100"), RUB);
        Money usd = new Money(new BigDecimal("100"), USD);

        assertThrows(IncompatibleCurrencies.class, () -> rub.subtract(usd));
    }

    @Test
    void should_throw_when_amount_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> new Money(null, RUB));
    }

    @Test
    void should_throw_when_currency_is_null() {
        assertThrows(IllegalArgumentException.class,
                () -> new Money(new BigDecimal("100"), null));
    }

    @Test
    void should_consider_100_0_and_100_00_equal() {
        Money a = new Money(new BigDecimal("100.0"), RUB);
        Money b = new Money(new BigDecimal("100.00"), RUB);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}