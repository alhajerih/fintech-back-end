package com.springboot.bankbackend.controller;

import com.springboot.bankbackend.bo.*;
import com.springboot.bankbackend.entity.*;
import com.springboot.bankbackend.service.UserService;
import com.springboot.bankbackend.service.auth.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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






}
