package com.example.mini_bank.jpa.entities;

import com.example.mini_bank.domain.models.Transaction;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TransactionEntity {
    @Id
    private long id = 0;
    @Embedded
    private Transaction transaction;

    public TransactionEntity(long id, Transaction transaction) {
        this.transaction = transaction;
    }

    public TransactionEntity(TransactionEntity other) {
        id = other.id();
        transaction = other.transaction();
    }

    protected TransactionEntity() { }

    public long id() {
        return id;
    }

    public Transaction transaction() {
        return new Transaction(transaction);
    }
}