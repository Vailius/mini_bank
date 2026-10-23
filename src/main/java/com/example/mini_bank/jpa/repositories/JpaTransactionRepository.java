package com.example.mini_bank.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mini_bank.jpa.entities.TransactionEntity;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByRequestId(UUID requestId);
    Optional<TransactionEntity> findById(UUID id);
    Page<TransactionEntity> findBySender(UUID sender);
    Page<TransactionEntity> findByRecivier(UUID reciver);
    Page<TransactionEntity> findBySenderAccount(UUID senderAccount);
    Page<TransactionEntity> findByRecivierAccount(UUID recivierAccount);
    boolean existsByRequestId(UUID requestId);
}