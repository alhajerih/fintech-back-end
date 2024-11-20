package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.UserResponse;
import com.springboot.bankbackend.entity.TransactionsEntity;
import com.springboot.bankbackend.entity.UserEntity;
import com.springboot.bankbackend.repository.BankRepository;
import com.springboot.bankbackend.repository.UserRepository;
import com.springboot.bankbackend.service.auth.CustomUserDetailsService;

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
        .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()))
        .collect(Collectors.toList());
  }

  public UserResponse getUserById(Long id) {
    UserEntity user = userRepository.getById(id);
    return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
  }

  @Override
  public UserResponse deleteUserById(Long id) {
    UserResponse user = getUserById(id);

    userRepository.deleteById(id);
    return user;
  }

  @Override
  public List<TransactionsEntity> getAllDeposits() {
    // todo implement get all deposits
    return null;
  }
}
