package com.example.mini_bank.domain.services;
import java.util.UUID;

import com.example.mini_bank.domain.exceptions.AccessDenied;
import com.example.mini_bank.domain.exceptions.NonExistentAccount;
import com.example.mini_bank.domain.models.Account;
import com.example.mini_bank.domain.models.Money;
import com.example.mini_bank.domain.models.Transaction;
import com.example.mini_bank.domain.repositories.AccountRepository;
import com.example.mini_bank.domain.repositories.TransactionRepository;

public class TransferService {
    
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    private Account loadAccount(UUID id) {
        return (Account) accountRepository
            .findById(id)
            .orElseThrow(
                () -> new NonExistentAccount(id)
        );
    }

    public Transaction transfer(UUID requestId, UUID currentUser, UUID accountSender, UUID accountRecivier, Money amount) {
        Account sender, recivier;
        if (accountSender.compareTo(accountRecivier) > 0) {
            sender = loadAccount(accountSender);
            recivier = loadAccount(accountRecivier);
        } else {
            recivier = loadAccount(accountRecivier);
            sender = loadAccount(accountSender);
        }

        if (!sender.userId().equals(currentUser)) {
            AccessDenied.throwAccessToSomeone(accountSender);
        }

        sender.debit(amount);
        recivier.credit(amount);
        Transaction transaction = new Transaction(
            requestId, 
            sender.userId(), recivier.userId(), 
            sender.id(), recivier.id(), 
            amount
        );
        accountRepository.save(sender);
        accountRepository.save(recivier);
        return transactionRepository.save(transaction);
    }
}