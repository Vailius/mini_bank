package com.example.mini_bank.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.example.mini_bank.domain.models.Account;
import com.example.mini_bank.domain.repositories.AccountRepository;
import com.example.mini_bank.jpa.entities.AccountEntity;

public class AccountRepositoryBinding implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryBinding(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = jpaAccountRepository;
    }

    private static Optional<Account> optionalToDomain(Optional<AccountEntity> entity) {
        if (entity.isPresent()) {
            return Optional.of(entity.get().account());
        }
        return Optional.ofNullable((Account)null);
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = jpaAccountRepository.save(new AccountEntity(0, account));
        if (entity == null) {
            return null;
        }
        return entity.account();
    }

    @Override
    public Optional<Account> findByNumber(String number) {
        return optionalToDomain(jpaAccountRepository.findByNumber(number));
    }

    @Override
    public Optional<Account> findByIdForUpdate(UUID id) {
        return optionalToDomain(jpaAccountRepository.findByIdForUpdate(id));
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return optionalToDomain(jpaAccountRepository.findById(id));
    }

    @Override
    public Page<Account> findByUserId(UUID userId) {
        Page<AccountEntity> page = jpaAccountRepository.findByUser(userId);
        return page.map((x) -> x.account());
    }

    @Override
    public Account delete(Account account) {
        Optional<AccountEntity> ent = jpaAccountRepository.findById(account.id());
        if (ent.isPresent()) {
            jpaAccountRepository.delete(ent.get());
            return account;
        }
        return null;
    }
}