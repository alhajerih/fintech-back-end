package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.BeneficiaryEntity;
import com.springboot.bankbackend.entity.SavingsEntity;
import com.springboot.bankbackend.entity.TransactionEntity;
import com.springboot.bankbackend.entity.UserEntity;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse updateProfile(UpdateProfileRequest request);
    UserResponse getProfile();
    TransactionEntity addTransaction(TransactionRequest request);
    List<TransactionEntity> getTransactions();
    UserEntity getUserProfileByUsername(String username);
    List<BeneficiaryEntity> getBeneficiariesByUsername(String username);
    BeneficiaryEntity addBeneficiary(String username, BeneficiaryRequest request);
    List<BeneficiaryEntity> getBeneficiary();
    SavingsEntity addSaving(String username, SavingRequest request);
    List<SavingsEntity> getSaving();
    SavingsEntity deleteSaving( Long id);
}
