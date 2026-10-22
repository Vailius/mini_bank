package com.example.mini_bank.domain.repositories;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.example.mini_bank.domain.exceptions.NullHelper;
import com.example.mini_bank.domain.models.Transaction;

public class InMemoryTransactionRepository implements TransactionRepository {

    private final ConcurrentHashMap<UUID, Transaction> transactionsOfId;
    private final ConcurrentHashMap<UUID, Transaction> transactionOfRequest;

    public InMemoryTransactionRepository() {
        transactionOfRequest = new ConcurrentHashMap<>();
        transactionsOfId = new ConcurrentHashMap<>();
    }

    @Override
    public Transaction save(Transaction transaction) {
        NullHelper.throwIfNull(transaction, "transaction");
        transactionsOfId.put(transaction.id(), transaction);
        transactionOfRequest.put(transaction.requestId(), transaction);
        return transaction;
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        NullHelper.throwIfNull(id, "id");
        return Optional.ofNullable(transactionsOfId.get(id));
    }

    @Override
    public Optional<Transaction> findByRequestId(UUID requestId) {
        NullHelper.throwIfNull(requestId, "requestId");
        return Optional.ofNullable(transactionOfRequest.get(requestId));
    }
}