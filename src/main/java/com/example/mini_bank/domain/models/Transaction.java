package com.example.mini_bank.domain.models;
import java.util.UUID;

import com.example.mini_bank.domain.exceptions.NullHelper;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class Transaction {
    private UUID requestId;
    private UUID id;
    private UUID recivier;
    private UUID sender;
    private UUID recivierAccount;
    private UUID senderAccount;
    @Embedded
    private Money amount;

    public Transaction(UUID requestId, UUID sender, UUID recivier, UUID senderAccount, UUID recivierAccount, Money amount) {
        NullHelper.throwIfNull(requestId, "requestId");
        NullHelper.throwIfNull(sender, "sender");
        NullHelper.throwIfNull(recivier, "recivier");
        NullHelper.throwIfNull(senderAccount, "senderAccount");
        NullHelper.throwIfNull(recivierAccount, "recivierAccount");
        NullHelper.throwIfNull(amount, "amount");
        this.requestId = requestId;
        this.id = UUID.randomUUID();
        this.recivier = recivier;
        this.sender = sender;
        this.recivierAccount = recivierAccount;
        this.senderAccount = senderAccount;
        this.amount = amount;
    }

    public Transaction(Transaction other) {
        requestId = other.requestId();
        id = other.id();
        recivier = other.recivier();
        sender = other.sender();
        recivierAccount = other.recivierAccount();
        senderAccount = other.senderAccount();
        amount = Money.copy(other.amount());
    }

    public UUID requestId() {
        return requestId;
    }

    public UUID id() {
        return id;
    }

    public UUID recivier() {
        return recivier;
    }

    public UUID sender() {
        return sender;
    }

    public UUID recivierAccount() {
        return recivierAccount;
    }

    public UUID senderAccount() {
        return senderAccount;
    }

    public Money amount() {
        return Money.copy(amount);
    }
}