package com.example.mini_bank.jpa.entities;

import com.example.mini_bank.domain.models.Account;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;

@Entity
public class AccountEntity {
    private long id;
    @Embedded
    private Account account;
    
    public AccountEntity (long id, Account account) {
        this.id = id;
        this.account = account;
    }

    public AccountEntity(AccountEntity other) {
        id = other.id();
        account = other.account();
    }

    protected AccountEntity() { }

    public long id() {
        return id;
    }

    public Account account() {
        return new Account(account);
    }
}