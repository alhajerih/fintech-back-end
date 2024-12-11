package com.springboot.bankbackend.service;


import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.*;
import com.springboot.bankbackend.repository.*;
import com.springboot.bankbackend.service.auth.CustomUserDetailsService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final CustomUserDetailsService userDetailsService;

  private final OpenAIService openAIService;

  public UserServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      CustomUserDetailsService userDetailsService,
      OpenAIService openAIService) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userDetailsService = userDetailsService;
    this.openAIService = openAIService;
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

    // todo put a real profile picture

    userEntity = userRepository.save(userEntity);

    UserResponse response =
        new UserResponse(userEntity.getId(), userEntity.getUsername());
    return response;
  }

  @Override
  public UserResponse updateProfile(UpdateProfileRequest request) {
    UserEntity user = getAuthenticatedUser();
    user.setUsername(request.getUsername());
    user.setId(user.getId());
    user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
    user = userRepository.save(user);
    UserResponse response = new UserResponse(user.getId(), user.getUsername());

    return response;
  }

  @Override
  public UserResponse getProfile() {
    UserEntity user = getAuthenticatedUser();

    // Build the UserResponse with filtered transactions
    UserResponse response = new UserResponse(user.getId(), user.getUsername());
    return response;
  }

  public UserEntity getUserProfile() {
    UserEntity user = getAuthenticatedUser();

    return user;
  }




  private UserEntity getAuthenticatedUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }



}
