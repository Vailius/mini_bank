package com.example.mini_bank.domain.repositories;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.mini_bank.domain.models.Account;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findByNumber(String number);
    Optional<Account> findByIdForUpdate(UUID id);
    Optional<Account> findById(UUID id);
    Page<Account> findByUserId(UUID userId);
    Account delete(Account account);
}