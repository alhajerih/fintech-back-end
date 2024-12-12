package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.*;

import java.util.List;

public interface UserService {
  UserResponse createUser(CreateUserRequest request);

  UserResponse updateProfile(UpdateProfileRequest request);

  UserEntity getUserProfile();

  List<UserEntity> getAllUsers();
  void deleteUser(Long id);
}
