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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Fetch the UserEntity from the database
    UserEntity userEntity = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Establish the relationship and save the transaction
    transaction.setUser(userEntity); // Set the user in the transaction
    transactionRepository.save(transaction);

    // Update the user's transaction list
    userEntity.getTransactions().add(transaction);
    userRepository.save(userEntity);

    return transaction;
  }

  @Override
  public List<TransactionEntity> getTransactions() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    CustomUserDetails user = userDetailsService.loadUserByUsername(username);
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

    //todo put a real profile picture
    userEntity.setProfilePicture("https://example.com/profile-picture.jpg");

    userEntity = userRepository.save(userEntity);

    UserResponse response =
        new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail());
    return response;
  }

  @Override
  public UserResponse updateProfile(UpdateProfileRequest request) {
    CustomUserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(request.getUsername());
    userEntity.setEmail(request.getEmail());
    userEntity.setId(user.getId());
    userEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));

    userEntity = userRepository.save(userEntity);
    UserResponse response =
            new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail());

    return response;
  }

  @Override
  public UserResponse getProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

    // Build the UserResponse with filtered transactions
    UserResponse response =
            new UserResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail());

    return response;
  }

  @Override
  public UserEntity getUserProfileByUsername(String username){

    UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

    return user;
  }

  @Override
  public List<BeneficiaryEntity> getBeneficiariesByUsername(String username) {
    UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("beneficiary Not Found"));

    List<BeneficiaryEntity> beneficiaryEntities = user.getBeneficiaries();

    return beneficiaryEntities;
  }

@Override
  public BeneficiaryEntity addBeneficiary(String username, BeneficiaryRequest request){
  UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" User Not Found "));
  BeneficiaryEntity beneficary = new BeneficiaryEntity();
  beneficary.setDoesNeedGroceries(request.getDoesNeedGroceries());
  beneficary.setGroceriesMultiplier(request.getGroceriesMultiplier());
  beneficary.setUser(user);
  BeneficiaryEntity userBeneficiary = beneficiaryRepository.save(beneficary);
  user.addBeneficiary(userBeneficiary);
  userRepository.save(user);

  return userBeneficiary;
}


  @Override
  public List<BeneficiaryEntity> getBeneficiary(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" User Not Found "));
    return user.getBeneficiaries();
}
@Override
public SavingsEntity addSaving(String username, SavingRequest request){
    UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" User Not Found"));
    SavingsEntity newSaving = new SavingsEntity();
    newSaving.setAmount(request.getAmount());
    newSaving.setAmountAllocatedPerMonth(request.getAmountAllocatedPerMonth());
    newSaving.setMonthsUntilDeadline(request.getMonthsUntilDeadline());
    newSaving.setName(request.getName());
    newSaving.setId(request.getId());
    SavingsEntity userSaving = savingsRepository.save(newSaving);
    user.addSaving(userSaving);
    userRepository.save(user);

    return userSaving;
}

@Override
  public List<SavingsEntity> getSaving(){
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  String username = authentication.getName();
  UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(" User Not Found "));

  return user.getSavings();
}

public SavingsEntity deleteSaving( Long id ){
  SavingsEntity saving = savingsRepository.getById(id);
  savingsRepository.deleteById(id);

  return saving;
}

}
