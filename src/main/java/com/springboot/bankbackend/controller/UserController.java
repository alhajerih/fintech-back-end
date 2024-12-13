package com.springboot.bankbackend.controller;

import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.*;
import com.springboot.bankbackend.service.UserService;
import com.springboot.bankbackend.service.auth.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

  private final UserService userService;
  private final CustomUserDetailsService userDetailsService;

  public UserController(UserService userService, CustomUserDetailsService userDetailsService) {
    this.userService = userService;
    this.userDetailsService = userDetailsService;
  }

  @GetMapping("/sayHi")
  public String sayHi() {
    return "Hi, you are an authenticated user";
  }

  // Get profile for logged-in user
  @GetMapping("/me")
  public ResponseEntity<UserEntity> getCurrentUserProfile() {
    UserEntity user = userService.getUserProfile();
    if(user != null) {
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }

  // Update profile for logged in user
  @PutMapping("/update-profile")
  ResponseEntity<UserResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
    UserResponse response = userService.updateProfile(request);

    // Check if the response is not null (indicating a successful update)
    if (response != null) {
      // Return a 201 Created status code along with some of the updated user data
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      // Handle the case where the update was not successful (e.g., validation failed)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }


  //Get user by ID for adding friends
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {

    return ResponseEntity.ok(userService.getUserById(id));
  }


  @PostMapping("/participate/event/{eventId}")
  public ResponseEntity<Void> participateInEvent( @PathVariable Long eventId) {
    Long userId = getLoggedInUserId();
    userService.participateInEvent(userId, eventId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/participate/daily/{dailyChallengeId}")
  public ResponseEntity<Void> participateInDailyChallenge( @PathVariable Long dailyChallengeId) {
    Long userId = getLoggedInUserId();
    userService.participateInDailyChallenge(userId, dailyChallengeId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/participate/friend/{challengeId}")
  public ResponseEntity<Void> participateInFriendChallenge(

          @PathVariable Long challengeId,
          @RequestBody List<Long> friendIds) {
    Long userId = getLoggedInUserId();
    userService.participateInFriendChallenge(userId, challengeId, friendIds);
    return ResponseEntity.ok().build();
  }


  // Utility method to get the logged-in user's ID
  private Long getLoggedInUserId() {
    // Assuming Spring Security is used with JWT
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity user = userService.findByUsername(username);
    return user.getId();
  }


  @PostMapping("/friends/{friendId}")
  public ResponseEntity<Void> addFriend(@PathVariable Long friendId) {
    Long userId = getLoggedInUserId(); // Get the logged-in user's ID
    userService.addFriend(userId, friendId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/friends")
  public ResponseEntity<List<UserResponse>> getAllFriends() {
    Long userId = getLoggedInUserId(); // Get the logged-in user's ID
    List<UserResponse> friends = userService.getAllFriends(userId);
    return ResponseEntity.ok(friends);
  }



  //Rest API`s for update the steps

  @PostMapping("/steps/daily/{dailyChallengeId}")
  public ResponseEntity<Void> updateStepsForDailyChallenge(
          @PathVariable Long dailyChallengeId,
          @RequestBody UpdateStepsRequest request) {
    Long userId = getLoggedInUserId(); // Get the logged-in user's ID
    userService.updateStepsForDailyChallenge(userId, dailyChallengeId, request.getSteps());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/steps/friend/{friendChallengeId}")
  public ResponseEntity<Void> updateStepsForFriendChallenge(
          @PathVariable Long friendChallengeId,
          @RequestBody UpdateStepsRequest request) {
    Long userId = getLoggedInUserId(); // Get the logged-in user's ID
    userService.updateStepsForFriendChallenge(userId, friendChallengeId, request.getSteps());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/steps/event/{eventId}")
  public ResponseEntity<Void> updateStepsForEvent(
          @PathVariable Long eventId,
          @RequestBody UpdateStepsRequest request) {
    Long userId = getLoggedInUserId(); // Get the logged-in user's ID
    userService.updateStepsForEvent(userId, eventId, request.getSteps());
    return ResponseEntity.ok().build();
  }

}
