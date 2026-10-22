package com.example.mini_bank.domain.services;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.mini_bank.domain.exceptions.AccessDenied;
import com.example.mini_bank.domain.exceptions.IncompatibleCurrencies;
import com.example.mini_bank.domain.exceptions.InsufficientFunds;
import com.example.mini_bank.domain.exceptions.SameAccountTransfer;
import com.example.mini_bank.domain.models.Account;
import com.example.mini_bank.domain.models.Money;
import com.example.mini_bank.domain.models.Transaction;
import com.example.mini_bank.domain.repositories.InMemoryAccountRepository;
import com.example.mini_bank.domain.repositories.InMemoryTransactionRepository;
public class TransferServiceTest {
    private TransferService service;
    private InMemoryAccountRepository accounts;
    private InMemoryTransactionRepository transactions;
    private Account petrovAccount;
    private Account sidorovAccount;

    static final UUID petrovId = UUID.randomUUID();
    static final UUID sidorovId = UUID.randomUUID();
    static final Currency currencyRub = Currency.getInstance("RUB");
    static final Currency currencyEur = Currency.getInstance("EUR");

    @BeforeEach
    void prepare() {
        accounts = new InMemoryAccountRepository();
        Money oneHandred = new Money(new BigDecimal("100.0"), currencyRub);
        petrovAccount = accounts.save(new Account(petrovId, oneHandred));
        sidorovAccount = accounts.save(new Account(sidorovId, oneHandred));
        transactions = new InMemoryTransactionRepository();
        service = new TransferService(
            accounts, transactions
        );
    }

    @Test
    void test_create_null() {
        assertThrows(IllegalArgumentException.class, () -> new TransferService(null, null));
        assertThrows(IllegalArgumentException.class, () -> new TransferService(null, transactions));
        assertThrows(IllegalArgumentException.class, () -> new TransferService(accounts, null));
    }

    @Test
    void test_transfer_null() {
        UUID tuid = UUID.randomUUID();
        Money amount = new Money(new BigDecimal("100.0"), Currency.getInstance("RUB"));
        assertThrows(IllegalArgumentException.class, () -> service.transfer(null, null, null, null, null));
        assertThrows(IllegalArgumentException.class, () -> service.transfer(null, tuid, tuid, tuid, amount));
        assertThrows(IllegalArgumentException.class, () -> service.transfer(tuid, null, tuid, tuid, amount));
        assertThrows(IllegalArgumentException.class, () -> service.transfer(tuid, tuid, null, tuid, amount));
        assertThrows(IllegalArgumentException.class, () -> service.transfer(tuid, tuid, tuid, null, amount));
        assertThrows(IllegalArgumentException.class, () -> service.transfer(tuid, tuid, tuid, tuid, null));
    }

    static void checkAccounts(Optional<Account> a, Optional<Account> b) {
        assertEquals(true, a.isPresent());
        assertEquals(true, b.isPresent());
    }

    static void checkAccountBalance(Account account, Money expectedBalance) {
        assertEquals(true, account.balance().equals(expectedBalance));
    }

    @Test
    void test_transfer_normal() {
        service.transfer(UUID.randomUUID(), petrovId, petrovAccount.id(), sidorovAccount.id(), new Money(new BigDecimal("10.0"), currencyRub));
        Optional<Account> updatedPetrov = accounts.findById(petrovAccount.id());
        Optional<Account> updatedSidorov = accounts.findById(sidorovAccount.id());
        checkAccounts(updatedPetrov, updatedSidorov);
        checkAccountBalance(updatedPetrov.get(), new Money(new BigDecimal("90.0"), currencyRub));
        checkAccountBalance(updatedSidorov.get(), new Money(new BigDecimal("110.0"), currencyRub));
    }

    @Test
    void test_transfer_from_different_account() {
        assertThrows(AccessDenied.class, () ->
            service.transfer(UUID.randomUUID(), sidorovAccount.userId(),
            petrovAccount.id(), sidorovAccount.id(),
            new Money(new BigDecimal("10.0"), currencyRub))
        );
    }

    @Test
    void test_transfer_different_currency() {
        assertThrows(IncompatibleCurrencies.class, () ->
            service.transfer(UUID.randomUUID(), sidorovAccount.userId(),
            sidorovAccount.id(), petrovAccount.id(),
            new Money(new BigDecimal("10.0"), currencyEur))
        );
    }

    @Test
    void test_transfer_same_account() {
        assertThrows(SameAccountTransfer.class, () -> 
            service.transfer(UUID.randomUUID(), petrovAccount.userId(),
                petrovAccount.id(), petrovAccount.id(),
                new Money(new BigDecimal("10.0"), currencyRub)
            )
        );
    }

    @Test
    void test_transfer_overbalance() {
        assertThrows(InsufficientFunds.class, () ->
            service.transfer(
                UUID.randomUUID(), petrovAccount.userId(),
                petrovAccount.id(), sidorovAccount.id(),
                new Money(new BigDecimal("10000.0"), currencyRub)
            )
        );
    }

    @Test
    void test_transfer_repeat_request() {
        Transaction transaction = service.transfer(UUID.randomUUID(), sidorovAccount.userId(),
            sidorovAccount.id(), petrovAccount.id(),
            new Money(new BigDecimal("10.0"), currencyRub)
        );
        Transaction repeat = service.transfer(transaction.requestId(), sidorovAccount.userId(),
            sidorovAccount.id(), petrovAccount.id(),
            new Money(new BigDecimal("50.0"), currencyRub)
        );

        assertEquals(transaction.requestId().equals(repeat.requestId()), true);
        assertEquals(transaction.amount().equals(repeat.amount()), true);
    }

    @Test
    void test_transfer_negative_value() {
        assertThrows(IllegalArgumentException.class,
            () -> service.transfer(
                UUID.randomUUID(), 
                petrovId, petrovAccount.id(), sidorovAccount.id(), 
                new Money(new BigDecimal("-10.0"), currencyRub))
        );
    }
}