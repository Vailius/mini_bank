package com.example.mini_bank.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import com.example.mini_bank.domain.models.Transaction;
import com.example.mini_bank.domain.repositories.TransactionRepository;
import com.example.mini_bank.jpa.entities.TransactionEntity;

public class TransactionRepositoryBinding implements TransactionRepository {

    private final JpaTransactionRepository jpaTransactionRepository;

    public TransactionRepositoryBinding(JpaTransactionRepository jpaTransactionRepository) {
        this.jpaTransactionRepository = jpaTransactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = jpaTransactionRepository.save(new TransactionEntity(0, transaction));
        if (entity == null) {
            return null;
        }
        return entity.transaction();
    }

    private static Optional<Transaction> optionalToDomain(Optional<TransactionEntity> entity) {
        if (entity.isPresent()) {
            return Optional.of(entity.get().transaction());
        }
        return Optional.ofNullable((Transaction)null);
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return optionalToDomain(jpaTransactionRepository.findById(id));
    }

    @Override
    public Optional<Transaction> findByRequestId(UUID requestId) {
        return optionalToDomain(jpaTransactionRepository.findByRequestId(requestId));
    }
}