package com.example.mini_bank.domain.services;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.mini_bank.domain.exceptions.AccountNotEmpty;
import com.example.mini_bank.domain.exceptions.NonExistentUser;
import com.example.mini_bank.domain.models.Account;
import com.example.mini_bank.domain.models.Money;
import com.example.mini_bank.domain.models.User;
import com.example.mini_bank.domain.repositories.InMemoryAccountRepository;
import com.example.mini_bank.domain.repositories.InMemoryUserRepository;


public class AccountServiceTest {
    private AccountService service;
    private InMemoryAccountRepository accountRepository;
    private InMemoryUserRepository userRepository;
    private User sidorov;
    private User petrov;

    @BeforeEach
    void prepare() {
        accountRepository = new InMemoryAccountRepository();
        userRepository = new InMemoryUserRepository();
        service = new AccountService(accountRepository, userRepository);
        sidorov = userRepository.save(new User("sidorov.paul@email.com", "12345678"));
        petrov = userRepository.save(new User("petrov.ugly@bk.com", "12345678"));
    }

    @Test
    void test_create_with_null() {
        InMemoryAccountRepository inMemoryAccountRepository = new InMemoryAccountRepository();
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        assertThrows(IllegalArgumentException.class, () -> new AccountService(null, null));
        assertThrows(IllegalArgumentException.class, () -> new AccountService(null, inMemoryUserRepository));
        assertThrows(IllegalArgumentException.class, () -> new AccountService(inMemoryAccountRepository, null));
    }

    @Test
    void test_non_existent_user_create() {
        assertThrows(NonExistentUser.class, () -> 
            service.createAccount(
                UUID.randomUUID(), 
                Currency.getInstance("RUB")
            )
        );
    }

    @Test
    void test_null_create() {
        assertThrows(IllegalArgumentException.class, () ->
            service.createAccount(null, Currency.getInstance("RUB"))
        );
        assertThrows(IllegalArgumentException.class, () ->
            service.createAccount(sidorov.id(), null)
        );
        assertThrows(IllegalArgumentException.class, () ->
            service.createAccount(null, null)
        );
    }

    @Test
    void test_null_close() {
        assertThrows(IllegalArgumentException.class, () ->
            service.closeAccount(null, UUID.randomUUID())
        );
        assertThrows(IllegalArgumentException.class, () ->
            service.closeAccount(sidorov.id(), null)
        );
        assertThrows(IllegalArgumentException.class, () ->
            service.closeAccount(null, null)
        );
    }

    @Test
    void test_happy_path_create() {
        Account account = service.createAccount(sidorov.id(), Currency.getInstance("RUB"));
        assertTrue(account != null);
        assertEquals(account.userId(), sidorov.id());
        Optional<Account> dbAccount = accountRepository.findById(account.id());
        assertEquals(dbAccount.isPresent(), true);
        Account dbInstance = dbAccount.get();
        assertEquals(dbInstance.userId(), sidorov.id());
        assertEquals(dbInstance.id(), account.id());
        assertEquals(dbInstance.number(), account.number());
        assertEquals(dbInstance.createdAt(), account.createdAt());
        assertEquals(dbInstance.balance(), account.balance());
    }

    @Test
    void test_happy_path_close() {
        Account account = service.createAccount(sidorov.id(), Currency.getInstance("RUB"));
        Account closed = service.closeAccount(account.userId(), account.id());
        assertEquals(account.userId(), sidorov.id());
        Optional<Account> dbAccount = accountRepository.findById(account.id());
        assertEquals(dbAccount.isPresent(), false);
        assertEquals(closed.id(), account.id());
        assertEquals(closed.userId(), account.userId());
    }

    @Test
    void test_close_with_money() {
        Account with_money = accountRepository.save(new Account(sidorov.id(), new Money(new BigDecimal("1000.0"), Currency.getInstance("RUB"))));
        assertThrows(AccountNotEmpty.class, () -> service.closeAccount(with_money.userId(), with_money.id()));
    }
}