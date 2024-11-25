package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.BeneficiaryEntity;
import com.springboot.bankbackend.entity.SavingsEntity;
import com.springboot.bankbackend.entity.TransactionEntity;
import com.springboot.bankbackend.entity.UserEntity;
import com.springboot.bankbackend.repository.BeneficiaryRepository;
import com.springboot.bankbackend.repository.SavingsRepository;
import com.springboot.bankbackend.repository.TransactionRepository;
import com.springboot.bankbackend.repository.UserRepository;
import com.springboot.bankbackend.service.auth.CustomUserDetailsService;
import com.springboot.bankbackend.utils.Roles;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final CustomUserDetailsService userDetailsService;
  private final TransactionRepository transactionRepository;
  private final BeneficiaryRepository beneficiaryRepository;
  private final SavingsRepository savingsRepository;

  public UserServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      CustomUserDetailsService userDetailsService,
      TransactionRepository transactionRepository,
      BeneficiaryRepository beneficiaryRepository,
      SavingsRepository savingsRepository) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userDetailsService = userDetailsService;
    this.transactionRepository = transactionRepository;
    this.beneficiaryRepository = beneficiaryRepository;
    this.savingsRepository = savingsRepository;
  }

  // Add transaction
  @Override
  public TransactionEntity addTransaction(TransactionRequest request) {
    // Create a new TransactionEntity and populate its fields
    TransactionEntity transaction = new TransactionEntity();
    transaction.setAmount(request.getAmount());
    transaction.setMessage(request.getMessage());
    transaction.setDateTime(LocalDateTime.now());
    transaction.setTransactionCategory(request.getTransactionCategory());
    transaction.setTransactionType(request.getTransactionType());

    // Get the currently authenticated user
    UserEntity user = getAuthenticatedUser();

    // Establish the relationship and save the transaction
    transaction.setUser(user); // Set the user in the transaction
    transactionRepository.save(transaction);

    // Update the user's transaction list
    user.getTransactions().add(transaction);
    userRepository.save(user);

    return transaction;
  }

  @Override
  public List<TransactionEntity> getTransactions() {
    UserEntity user = getAuthenticatedUser();
    return user.getTransactions();
  }

  @Override
  public UserResponse createUser(CreateUserRequest request) {

    UserEntity userEntity = new UserEntity();

    // Check if there is already a user with the same username
    if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
      throw new RuntimeException("Username already exists");
    }

    userEntity.setUsername(request.getUsername());
    userEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
    userEntity.setEmail(request.getEmail());
    userEntity.setRole(Roles.user);

    // todo put a real profile picture
    userEntity.setProfilePicture("https://example.com/profile-picture.jpg");

    userEntity = userRepository.save(userEntity);

    UserResponse response =
        new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail());
    return response;
  }

  @Override
  public UserResponse updateProfile(UpdateProfileRequest request) {
    UserEntity user = getAuthenticatedUser();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setId(user.getId());
    user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
    user = userRepository.save(user);
    UserResponse response =
        new UserResponse(user.getId(), user.getUsername(), user.getEmail());

    return response;
  }

  @Override
  public UserResponse getProfile() {
    UserEntity user = getAuthenticatedUser();

    // Build the UserResponse with filtered transactions
    UserResponse response =
        new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    return response;
  }

  public UserEntity getUserProfile() {
    UserEntity user = getAuthenticatedUser();

    return user;
  }

  public List<BeneficiaryEntity> getBeneficiaries() {
    UserEntity user = getAuthenticatedUser();
    List<BeneficiaryEntity> beneficiaryEntities = user.getBeneficiaries();

    return beneficiaryEntities;
  }

  @Override
  public BeneficiaryEntity addBeneficiary(BeneficiaryRequest request) {
    UserEntity user = getAuthenticatedUser();
    BeneficiaryEntity beneficary = new BeneficiaryEntity();
    beneficary.setDoesNeedGroceries(request.getDoesNeedGroceries());
    beneficary.setGroceriesMultiplier(request.getGroceriesMultiplier());
    beneficary.setName(request.getName());
    beneficary.setUser(user);
    BeneficiaryEntity userBeneficiary = beneficiaryRepository.save(beneficary);
    user.addBeneficiary(userBeneficiary);
    userRepository.save(user);

    return userBeneficiary;
  }

  @Override
  public List<BeneficiaryEntity> getBeneficiary() {
    UserEntity user = getAuthenticatedUser();
    return user.getBeneficiaries();
  }

  @Override
  public SavingsEntity addSaving(SavingRequest request) {
    UserEntity user = getAuthenticatedUser();
    SavingsEntity newSaving = new SavingsEntity();
    newSaving.setAmount(request.getAmount());
    newSaving.setAmountAllocatedPerMonth(request.getAmountAllocatedPerMonth());
    newSaving.setMonthsUntilDeadline(request.getMonthsUntilDeadline());
    newSaving.setName(request.getName());
    newSaving.setUser(user);

    SavingsEntity userSaving = savingsRepository.save(newSaving);
    user.addSaving(userSaving);
    userRepository.save(user);

    return userSaving;
  }

  @Override
  public List<SavingsEntity> getSaving() {
    UserEntity user = getAuthenticatedUser();
    return user.getSavings();
  }

  public SavingsEntity deleteSaving(Long id) {
    SavingsEntity saving = savingsRepository.getById(id);
    savingsRepository.deleteById(id);
    return saving;
  }

  private UserEntity getAuthenticatedUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
