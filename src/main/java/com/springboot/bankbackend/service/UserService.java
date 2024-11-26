package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.*;
import java.util.List;

public interface UserService {
  UserResponse createUser(CreateUserRequest request);

  UserResponse updateProfile(UpdateProfileRequest request);

  UserResponse getProfile();

  // Transaction
  TransactionEntity addTransaction(TransactionRequest request);

  List<TransactionEntity> getTransactions();

  UserEntity getUserProfile();

  // Beneficiary
  List<BeneficiaryEntity> getBeneficiaries();

  BeneficiaryEntity addBeneficiary(BeneficiaryRequest request);

  List<BeneficiaryEntity> getBeneficiary();

  // Savings
  SavingsEntity addSaving(SavingRequest request);

  List<SavingsEntity> getSaving();

  SavingsEntity deleteSaving(Long id);

  // Fixed payment
  FixedPaymentEntity addFixedPayment(FixedPaymentRequest request, Long benficiaryId);

  List<FixedPaymentEntity> getFixedPayment(Long beneficiaryId);
}
