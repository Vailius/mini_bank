package com.example.mini_bank.jpa.services;

import java.util.Currency;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.mini_bank.domain.models.Account;
import com.example.mini_bank.domain.services.AccountService;
import com.example.mini_bank.jpa.entities.AccountEntity;
import com.example.mini_bank.jpa.repositories.AccountRepositoryBinding;
import com.example.mini_bank.jpa.repositories.JpaAccountRepository;
import com.example.mini_bank.jpa.repositories.JpaUserRepository;
import com.example.mini_bank.jpa.repositories.UserRepositoryBinding;

import jakarta.transaction.Transactional;

@Service
public class JpaAccountService {
    private final AccountService domainService;
    private final JpaAccountRepository accountRepository;

    public JpaAccountService(JpaAccountRepository accountRepository, JpaUserRepository userRepository) {
        domainService = new AccountService(
            new AccountRepositoryBinding(accountRepository),
            new UserRepositoryBinding(userRepository)
        );
        this.accountRepository = accountRepository;
    }

    public AccountEntity createAccount(UUID userId, Currency currency) {
        Account account = domainService.createAccount(userId, currency);
        if (account == null) {
            return null;
        }
        return accountRepository.findById(account.id()).get();
    }

    @Transactional
    public AccountEntity closeAccount(UUID userId, UUID accountId) {
        Account deleted = domainService.closeAccount(userId, accountId);
        if (deleted == null) {
            return null;
        }
        return accountRepository.findById(accountId).get();
    }
}