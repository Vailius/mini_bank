package com.example.mini_bank.jpa.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.mini_bank.domain.models.Money;
import com.example.mini_bank.domain.models.Transaction;
import com.example.mini_bank.domain.services.TransferService;
import com.example.mini_bank.jpa.entities.TransactionEntity;
import com.example.mini_bank.jpa.repositories.AccountRepositoryBinding;
import com.example.mini_bank.jpa.repositories.JpaAccountRepository;
import com.example.mini_bank.jpa.repositories.JpaTransactionRepository;
import com.example.mini_bank.jpa.repositories.TransactionRepositoryBinding;

import jakarta.transaction.Transactional;

@Service
public class JpaTransferService {
    private final TransferService domainService;
    private final JpaTransactionRepository transactionRepository;

    public JpaTransferService(JpaTransactionRepository transactionRepository, JpaAccountRepository accountRepository) {
        domainService = new TransferService(
            new AccountRepositoryBinding(accountRepository),
            new TransactionRepositoryBinding(transactionRepository)
        );
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionEntity transfer(UUID requestId, UUID currentUser, UUID accountSender, UUID accountRecivier, Money amount) {
        Transaction transaction = domainService.transfer(requestId, currentUser, accountSender, accountRecivier, amount);
        if (transaction == null) {
            return null;
        }
        return transactionRepository.findByRequestId(requestId).get();
    }
}