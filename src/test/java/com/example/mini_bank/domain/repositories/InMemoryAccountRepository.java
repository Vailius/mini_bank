package com.example.mini_bank.domain.repositories;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.domain.Page;

import com.example.mini_bank.domain.exceptions.NullHelper;
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
        NullHelper.throwIfNull(account, "account");
        accountsByNumber.put(account.number(), account);
        accountsById.put(account.id(), account);
        return account;
    }

    @Override
    public Optional<Account> findByNumber(String number) {
        NullHelper.throwIfNull(number, "number");
        return Optional.ofNullable(accountsByNumber.get(number));
    }

    @Override
    public Optional<Account> findById(UUID id) {
        NullHelper.throwIfNull(id, "id");
        return Optional.ofNullable(accountsById.get(id));
    }

    @Override
    public Page<Account> findByUserId(UUID userId) {
        NullHelper.throwIfNull(userId, "userId");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Optional<Account> findByIdForUpdate(UUID id) {
        NullHelper.throwIfNull(id, "id");
        Optional<Account> account = findById(id);
        if (account.isPresent()) {
            Account forUpdate = new Account(account.get());
            return Optional.of(forUpdate);
        }
        return account;
    }

    @Override
    public Account delete(Account account) {
        NullHelper.throwIfNull(account, "account");
        accountsByNumber.remove(account.number());
        accountsById.remove(account.id());
        return account;
    }
}