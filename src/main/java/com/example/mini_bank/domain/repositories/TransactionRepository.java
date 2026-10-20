package com.example.mini_bank.domain.repositories;
import java.util.Optional;
import java.util.UUID;

import com.example.mini_bank.domain.models.Transaction;


public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(UUID id);
    Optional<Transaction> findByRequestId(UUID requestId);
}