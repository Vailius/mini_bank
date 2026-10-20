package com.example.mini_bank.domain.models;
import java.util.UUID;

public class Transaction {
    private final UUID requestId;
    private final UUID id;
    private final UUID recivier;
    private final UUID sender;
    private final UUID recivierAccount;
    private final UUID senderAccount;
    private final Money amount;

    public Transaction(UUID requestId, UUID sender, UUID recivier, UUID senderAccount, UUID recivierAccount, Money amount) {
        this.requestId = requestId;
        this.id = UUID.randomUUID();
        this.recivier = recivier;
        this.sender = sender;
        this.recivierAccount = recivierAccount;
        this.senderAccount = senderAccount;
        this.amount = amount;
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
        return new Money(amount.amount(), amount.currency());
    }
}