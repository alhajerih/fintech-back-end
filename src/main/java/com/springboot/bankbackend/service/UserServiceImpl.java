package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.BankAccountEntity;
import com.springboot.bankbackend.entity.TransactionsEntity;
import com.springboot.bankbackend.entity.UserEntity;
import com.springboot.bankbackend.repository.BankRepository;
import com.springboot.bankbackend.repository.UserRepository;
import com.springboot.bankbackend.service.auth.CustomUserDetailsService;
import com.springboot.bankbackend.utils.Roles;
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
    userEntity.setEmail(request.getEmail());

    userEntity.setRole(Roles.user);

    //todo put a real profile picture
    userEntity.setProfilePicture("https://example.com/profile-picture.jpg");

    // Save UserEntity first to make it persistent
    userEntity = userRepository.save(userEntity);

    // Create and associate the BankAccountEntity
    BankAccountEntity bankAccount = new BankAccountEntity();
    bankAccount.setUser(userEntity); // Set the user reference in BankAccountEntity
    bankAccount.setBalance(0.0);

    // Save the BankAccountEntity
    bankAccount = bankRepository.save(bankAccount);

    // Update the userEntity with the bank account association and save it again
    userEntity.addBankAccount(bankAccount);
    userEntity = userRepository.save(userEntity);

    UserResponse response =
        new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getEmail());
    return response;
  }

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

  public UserResponse getProfile() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

    // Build the UserResponse with filtered transactions
    UserResponse response =
            new UserResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail());

    return response;
  }

  public UpdateBalanceResponse updateBalance(UpdateBalanceRequest request) {
    // Get the username of the currently logged-in user from the security context
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Load user details using CustomUserDetailsService
    CustomUserDetails loggedInUserDetails = userDetailsService.loadUserByUsername(username);
    Long loggedInUserId = loggedInUserDetails.getId();

    // todo inplement update balance
    return null;
  }
}
