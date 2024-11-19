package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.BankAccountEntity;
import com.springboot.bankbackend.entity.TransactionEntity;
import com.springboot.bankbackend.entity.UserEntity;
import com.springboot.bankbackend.repository.BankRepository;
import com.springboot.bankbackend.repository.UserRepository;
import com.springboot.bankbackend.service.auth.CustomUserDetailsService;
import com.springboot.bankbackend.utils.Roles;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final CustomUserDetailsService userDetailsService;
  private final BankRepository bankRepository;

  public UserServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      CustomUserDetailsService userDetailsService,
      BankRepository bankRepository) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userDetailsService = userDetailsService;
    this.bankRepository = bankRepository;
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
    userEntity.setRole(Roles.valueOf(request.getRole()));
    userEntity.setAddress((request.getAddress()));
    userEntity.setPhoneNumber(request.getPhoneNumber());
    userEntity.setEmail(request.getEmail());

    // Save UserEntity first to make it persistent
    userEntity = userRepository.save(userEntity);

    // Create and associate the BankAccountEntity
    BankAccountEntity bankAccount = new BankAccountEntity();
    bankAccount.setUser(userEntity); // Set the user reference in BankAccountEntity
    bankAccount.setBalance(0.0);

    // Save the BankAccountEntity
    bankAccount = bankRepository.save(bankAccount);

    // Update the userEntity with the bank account association and save it again
    userEntity.setBankAccount(bankAccount);
    userEntity = userRepository.save(userEntity);

    UserResponse response =
        new UserResponse(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getRole().toString(),
            userEntity.getAddress(),
            userEntity.getPhoneNumber(),
            userEntity.getEmail(),
            bankAccount.getBalance(),
            bankAccount.getId(),
            userEntity.getTransactions());
    return response;
  }

  public UserResponse updateProfile(UpdateProfileRequest request) {
    CustomUserDetails user = userDetailsService.loadUserByUsername(request.getUsername());

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(request.getUsername());
    userEntity.setAddress(request.getAddress());
    userEntity.setEmail(request.getEmail());
    userEntity.setRole(Roles.valueOf(request.getRole()));
    userEntity.setPhoneNumber(request.getPhoneNumber());
    userEntity.setId(user.getId());
    userEntity.setTransactions(user.getTransactions());
    userEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
    userEntity.setBankAccount(user.getBankAccount());

    userEntity = userRepository.save(userEntity);
    UserResponse response =
        new UserResponse(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getRole().toString(),
            userEntity.getAddress(),
            userEntity.getPhoneNumber(),
            userEntity.getEmail(),
            userEntity.getBankAccount().getBalance(),
            userEntity.getBankAccount().getId(),
            userEntity.getTransactions());
    return response;
  }

  public UserResponse getProfile(String filterBefore, String filterAfter) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

    // Parse the date filters if they are provided
    LocalDate beforeDate = filterBefore != null ? LocalDate.parse(filterBefore, DateTimeFormatter.ISO_DATE) : null;
    LocalDate afterDate = filterAfter != null ? LocalDate.parse(filterAfter, DateTimeFormatter.ISO_DATE) : null;

    // Filter transactions based on the date range
    List<TransactionEntity> filteredTransactions = userDetails.getTransactions().stream()
            .filter(transaction -> {
              LocalDate transactionDate = transaction.getTransactionDate().toLocalDate();
              boolean isAfter = (afterDate == null || transactionDate.isAfter(afterDate) || transactionDate.isEqual(afterDate));
              boolean isBefore = (beforeDate == null || transactionDate.isBefore(beforeDate) || transactionDate.isEqual(beforeDate));
              return isAfter && isBefore;
            })
            .collect(Collectors.toList());

    // Build the UserResponse with filtered transactions
    UserResponse response = new UserResponse(
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getRole(),
            userDetails.getAddress(),
            userDetails.getPhoneNumber(),
            userDetails.getEmail(),
            userDetails.getBankAccount().getBalance(),
            userDetails.getBankAccount().getId(),
            filteredTransactions); // Pass the filtered transactions

    return response;
  }

  public UpdateBalanceResponse updateBalance(UpdateBalanceRequest request) {
    // Get the username of the currently logged-in user from the security context
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Load user details using CustomUserDetailsService
    CustomUserDetails loggedInUserDetails = userDetailsService.loadUserByUsername(username);
    Long loggedInUserId = loggedInUserDetails.getId();

    // Fetch the user entity from the database to ensure it is in sync
    UserEntity userEntity =
        userRepository
            .findById(loggedInUserId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Get the user's bank account and current balance
    BankAccountEntity userBankAccount = userEntity.getBankAccount();
    double newBalance;

    UpdateBalanceResponse updateBalanceResponse = new UpdateBalanceResponse();

    // Check transaction type and apply logic for balance update
    if ("TRANSFER".equalsIgnoreCase(request.getTransactionType().toString())) {
      // Transfer logic
      // Reduce balance from the logged-in user
      newBalance = userBankAccount.getBalance() - request.getAmount();
      userBankAccount.setBalance(newBalance);

      // Find the recipient user and update their balance
      UserEntity recipientUser =
          userRepository
              .findById(request.getToUserId())
              .orElseThrow(() -> new RuntimeException("Recipient user not found"));
      BankAccountEntity recipientBankAccount = recipientUser.getBankAccount();
      recipientBankAccount.setBalance(recipientBankAccount.getBalance() + request.getAmount());

      // Save transaction for the sender
      TransactionEntity senderTransaction = new TransactionEntity();
      senderTransaction.setUser(userEntity);
      senderTransaction.setAmount(-request.getAmount());
      senderTransaction.setFromUserId(loggedInUserId);
      senderTransaction.setToUserId(request.getToUserId());
      senderTransaction.setTransactionDate(LocalDateTime.now());
      senderTransaction.setTransactionType(request.getTransactionType());
      userEntity.getTransactions().add(senderTransaction);

      // Save transaction for the recipient
      TransactionEntity recipientTransaction = new TransactionEntity();
      recipientTransaction.setUser(recipientUser);
      recipientTransaction.setAmount(request.getAmount());
      recipientTransaction.setFromUserId(loggedInUserId);
      recipientTransaction.setToUserId(request.getToUserId());
      recipientTransaction.setTransactionDate(LocalDateTime.now());
      recipientTransaction.setTransactionType(request.getTransactionType());
      recipientUser.getTransactions().add(recipientTransaction);

      updateBalanceResponse.setFromUserId(loggedInUserId);
      updateBalanceResponse.setToUserId(request.getToUserId());

      // Save both users to update their balances and transactions
      userRepository.save(userEntity);
      userRepository.save(recipientUser);

    } else if ("DEPOSIT".equalsIgnoreCase(request.getTransactionType().toString())) {
      // Deposit logic: Increase balance of the logged-in user
      newBalance = userBankAccount.getBalance() + request.getAmount();
      userBankAccount.setBalance(newBalance);

      updateBalanceResponse.setFromUserId(loggedInUserId);
      updateBalanceResponse.setToUserId(loggedInUserId);

      // Save transaction for deposit
      TransactionEntity depositTransaction = new TransactionEntity();
      depositTransaction.setUser(userEntity);
      depositTransaction.setAmount(request.getAmount());
      depositTransaction.setFromUserId(loggedInUserId);
      depositTransaction.setToUserId(loggedInUserId); // for self-deposit
      depositTransaction.setTransactionDate(LocalDateTime.now());
      depositTransaction.setTransactionType(request.getTransactionType());
      userEntity.getTransactions().add(depositTransaction);

      userRepository.save(userEntity);

    } else if ("WITHDRAW".equalsIgnoreCase(request.getTransactionType().toString())) {
      // Withdraw logic: Decrease balance of the logged-in user
      newBalance = userBankAccount.getBalance() - request.getAmount();
      userBankAccount.setBalance(newBalance);

      updateBalanceResponse.setFromUserId(loggedInUserId);
      updateBalanceResponse.setToUserId(request.getToUserId());

      // Save transaction for withdrawal
      TransactionEntity withdrawalTransaction = new TransactionEntity();
      withdrawalTransaction.setUser(userEntity);
      withdrawalTransaction.setAmount(-request.getAmount());
      withdrawalTransaction.setFromUserId(loggedInUserId);
      withdrawalTransaction.setToUserId(loggedInUserId); // for self-withdrawal
      withdrawalTransaction.setTransactionDate(LocalDateTime.now());
      withdrawalTransaction.setTransactionType(request.getTransactionType());
      userEntity.getTransactions().add(withdrawalTransaction);

      userRepository.save(userEntity);
    } else {
      throw new RuntimeException("Invalid transaction type");
    }

    updateBalanceResponse.setNewBalance(newBalance);

    return updateBalanceResponse;
  }
}
