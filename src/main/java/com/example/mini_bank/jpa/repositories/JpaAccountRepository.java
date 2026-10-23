package com.example.mini_bank.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.example.mini_bank.jpa.entities.AccountEntity;

import jakarta.persistence.LockModeType;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findById(UUID id);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<AccountEntity> findByIdForUpdate(UUID id);
    Optional<AccountEntity> findByNumber(String number);
    boolean existsById(UUID id);
    Page<AccountEntity> findByUser(UUID userId);
}