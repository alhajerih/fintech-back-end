package com.springboot.bankbackend.service;


import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.*;
import com.springboot.bankbackend.repository.*;
import com.springboot.bankbackend.service.auth.CustomUserDetailsService;

import com.springboot.bankbackend.utils.Roles;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final CustomUserDetailsService userDetailsService;

//  private final OpenAIService openAIService;

  public UserServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder,
      CustomUserDetailsService userDetailsService
      ) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userDetailsService = userDetailsService;
  }




// create new user
  @Override
  public UserResponse createUser(CreateUserRequest request) {

    UserEntity userEntity = new UserEntity();

    // Check if there is already a user with the same username
    if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
      throw new RuntimeException("Username already exists");
    }
// Create a new UserEntity
    userEntity.setUsername(request.getUsername());
    userEntity.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
userEntity.setTotalSteps(request.getTotalSteps());
userEntity.setCity(request.getCity());
userEntity.setAge(request.getAge());
userEntity.setPhoneNumber(request.getPhoneNumber());
userEntity.setHeight(request.getHeight());
userEntity.setWeight(request.getWeight());
userEntity.setRole(Roles.user);



    userEntity = userRepository.save(userEntity);

    UserResponse response =
        new UserResponse(userEntity.getId(), userEntity.getUsername(),userEntity.getAge(),
                userEntity.getCity(),userEntity.getTotalSteps(),
                userEntity.getWeight(),userEntity.getHeight(),userEntity.getRole().toString()
        );
    return response;
  }

  @Override
  public UserResponse updateProfile(UpdateProfileRequest request) {
    UserEntity user = getAuthenticatedUser();
    user.setUsername(request.getUsername());
    user.setId(user.getId());
    user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
    user.setAge(request.getAge());
    user.setHeight(request.getHeight());
    user.setWeight(request.getWeight());
    user.setCity(request.getCity());
    user.setPhoneNumber(request.getPhoneNumber());
    user = userRepository.save(user);
    UserResponse response = new UserResponse(user.getId(), user.getUsername(),user.getAge(),user.getCity(),
            user.getTotalSteps(),user.getWeight(),user.getHeight(),user.getRole().toString());

    return response;
  }

  public UserResponse getProfile() {
    UserEntity user = getAuthenticatedUser();

    // Build the UserResponse with filtered transactions
    UserResponse response = new UserResponse(user.getId(), user.getUsername(),user.getAge(),user.getCity(),
            user.getTotalSteps(),user.getWeight(),user.getHeight(),user.getRole().toString());
    return response;
  }



  public UserEntity getUserProfile() {
    UserEntity user = getAuthenticatedUser();

    return user;
  }


  // get all users
  @Override
  public List<UserResponse> getAllUsers() {
    return userRepository.findAll()
            .stream()
            .map(user -> new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getAge(),
                    user.getCity(),
                    user.getTotalSteps(),
                    user.getWeight(),
                    user.getHeight(),
                    user.getRole().toString()
            ))
            .collect(Collectors.toList());
  }


  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }


    private UserEntity getAuthenticatedUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Override
  public UserResponse getUserById(Long id) {
    return userRepository.findById(id)
            .map(user -> new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getAge(),
                    user.getCity(),
                    user.getTotalSteps(),
                    user.getWeight(),
                    user.getHeight(),
                    user.getRole().toString()
            ))
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

  }

}
