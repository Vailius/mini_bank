package com.example.mini_bank.domain.services;

import java.util.Currency;
import java.util.UUID;

import com.example.mini_bank.domain.exceptions.AccessDenied;
import com.example.mini_bank.domain.exceptions.AccountNotEmpty;
import com.example.mini_bank.domain.exceptions.NonExistentAccount;
import com.example.mini_bank.domain.exceptions.NonExistentUser;
import com.example.mini_bank.domain.exceptions.NullHelper;
import com.example.mini_bank.domain.models.Account;
import com.example.mini_bank.domain.repositories.AccountRepository;
import com.example.mini_bank.domain.repositories.UserRepository;

public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService (AccountRepository accountRepository, UserRepository userRepository) {
        NullHelper.throwIfNull(accountRepository, "accountRepository");
        NullHelper.throwIfNull(userRepository, "userRepository");

        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(UUID userId, Currency currency) {
        NullHelper.throwIfNull(userId, "userId");
        NullHelper.throwIfNull(currency, "currency");

        if (!userRepository.findById(userId).isPresent()) {
            throw new NonExistentUser(userId);
        }

        Account account = new Account(userId, currency);
        return accountRepository.save(account);
    }

    public Account closeAccount(UUID user, UUID accountId) {
        NullHelper.throwIfNull(user, "user");
        NullHelper.throwIfNull(accountId, "accountId");

        if (!userRepository.findById(user).isPresent()) {
            throw new NonExistentUser(user);
        }

        Account account = accountRepository.findByIdForUpdate(accountId).orElseThrow(() -> new NonExistentAccount(accountId));
        if (!user.equals(account.userId())) {
            throw new AccessDenied("Cannot close other account");
        }

        if (!account.balance().isZero()) {
            throw new AccountNotEmpty(accountId);
        }
        
        return accountRepository.delete(account);
    }
}