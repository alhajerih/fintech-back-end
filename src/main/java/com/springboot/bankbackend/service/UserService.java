package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.TransactionEntity;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateProfile(UpdateProfileRequest request);
    UserResponse getProfile();
    TransactionEntity addTransaction(TransactionRequest request);
    List<TransactionEntity> getTransactions();
    public UpdateBalanceResponse updateBalance(UpdateBalanceRequest request);
    //    UserBalanceResponse withdraw(UserTransactionRequest request);
}
