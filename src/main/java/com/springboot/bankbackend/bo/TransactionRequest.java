package com.springboot.bankbackend.bo;

import com.springboot.bankbackend.utils.TransactionCategory;
import com.springboot.bankbackend.utils.TransactionType;

import java.time.LocalDateTime;

// todo remove as only for testing
public class TransactionRequest {
    private Double amount;
    private String message;
    private TransactionCategory transactionCategory;
    private TransactionType transactionType;

    public TransactionRequest() {}

    public TransactionRequest(Double amount, String message, TransactionCategory transactionCategory, TransactionType transactionType) {
        this.amount = amount;
        this.message = message;
        this.transactionCategory = transactionCategory;
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(TransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
