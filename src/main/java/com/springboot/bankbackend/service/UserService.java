package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.*;

import java.util.List;

public interface UserService {
  UserResponse createUser(CreateUserRequest request);

  UserResponse updateProfile(UpdateProfileRequest request);

  UserEntity getUserProfile();

  List<UserResponse> getAllUsers();
  void deleteUser(Long id);

  UserResponse getUserById(Long id);

  void participateInFriendChallenge(Long userId, Long challengeId, List<Long> friendIds);
  void participateInEvent(Long userId,Long eventId);
  void participateInDailyChallenge(Long userId, Long dailyChallengeId);
UserEntity findByUsername(String username);
  void addFriend(Long userId, Long friendId);
  List<UserResponse> getAllFriends(Long userId);
}
