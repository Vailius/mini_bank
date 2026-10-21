package com.example.mini_bank.domain.repositories;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.domain.Page;

import com.example.mini_bank.domain.models.Account;

public class InMemoryAccountRepository implements AccountRepository {
    private final ConcurrentHashMap<String, Account> accountsByNumber;
    private final ConcurrentHashMap<UUID, Account> accountsById;

    public int count() {
        return accountsById.size();
    }

    public InMemoryAccountRepository() {
        accountsById = new ConcurrentHashMap<>();
        accountsByNumber = new ConcurrentHashMap<>();
    }

    @Override
    public Account save(Account account) {
        accountsByNumber.put(account.number(), account);
        accountsById.put(account.id(), account);
        return account;
    }

    @Override
    public Optional<Account> findByNumber(String number) {
        return Optional.ofNullable(accountsByNumber.get(number));
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return Optional.ofNullable(accountsById.get(id));
    }

    @Override
    public Page<Account> findByUserId(UUID userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Account> findByIdForUpdate(UUID id) {
        Optional<Account> account = findById(id);
        if (account.isPresent()) {
            Account forUpdate = new Account(account.get());
            return Optional.of(forUpdate);
        }
        return account;
    }
}