package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.UserResponse;
import com.springboot.bankbackend.entity.TransactionEntity;
import com.springboot.bankbackend.entity.UserEntity;
import com.springboot.bankbackend.repository.BankRepository;
import com.springboot.bankbackend.repository.UserRepository;
import com.springboot.bankbackend.service.auth.CustomUserDetailsService;
import com.springboot.bankbackend.utils.TransactionType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final CustomUserDetailsService userDetailsService;
  private final BankRepository bankRepository;

  public AdminServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      CustomUserDetailsService userDetailsService,
      BankRepository bankRepository) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userDetailsService = userDetailsService;
    this.bankRepository = bankRepository;
  }

  public List<UserResponse> getAllUsers() {
    List<UserEntity> users = userRepository.findAll();

    // Map each UserEntity to UserProfileResponse and return as a list
    return users.stream()
        .map(
            user ->
                new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getRole().toString(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    user.getBankAccount().getBalance(),
                    user.getBankAccount().getId(),
                    user.getTransactions()))
        .collect(Collectors.toList());
  }

  public UserResponse getUserById(Long id) {
    UserEntity user = userRepository.getById(id);
    return new UserResponse(
        user.getId(),
        user.getUsername(),
        user.getRole().toString(),
        user.getEmail(),
        user.getPhoneNumber(),
        user.getAddress(),
        user.getBankAccount().getBalance(),
        user.getBankAccount().getId(),
            user.getTransactions());
  }

  @Override
  public UserResponse deleteUserById(Long id) {
    UserResponse user = getUserById(id);

    userRepository.deleteById(id);
    return user;
  }

  @Override
  public List<TransactionEntity> getAllDeposits() {
    List<UserEntity> users = userRepository.findAll();
    List<TransactionEntity> depositTransactions = new ArrayList<>();

    for (UserEntity user : users) {
      for (TransactionEntity transaction : user.getTransactions()) {
        if (transaction.getTransactionType().equals(TransactionType.DEPOSIT)) {
          depositTransactions.add(transaction);
        }
      }
    }

    return depositTransactions;
  }

}
