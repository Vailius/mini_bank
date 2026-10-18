package com.example.mini_bank.domain.repositories;
import com.exmaple.mini_bank.domain.models.Account;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findByNumber(String number);
    Optional<Account> findById(UUID id);
    Page<Account> findByUserId(UUID userId);
}