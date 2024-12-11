package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.UserResponse;
import com.springboot.bankbackend.entity.UserEntity;
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

  public AdminServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      CustomUserDetailsService userDetailsService) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public List<UserResponse> getAllUsers() {
    List<UserEntity> users = userRepository.findAll();

    // Map each UserEntity to UserProfileResponse and return as a list
    return users.stream()
        .map(user -> new UserResponse(user.getId(), user.getUsername(),user.getKilo(),user.getAddress(),user.getTotalSteps()))
        .collect(Collectors.toList());
  }

  @Override
  public UserResponse getUserById(Long id) {
    UserEntity user = userRepository.getById(id);
    return new UserResponse(user.getId(), user.getUsername(),user.getKilo(),user.getAddress(),user.getTotalSteps());
  }

  @Override
  public UserResponse deleteUserById(Long id) {
    UserResponse user = getUserById(id);

    userRepository.deleteById(id);
    return user;
  }


}
