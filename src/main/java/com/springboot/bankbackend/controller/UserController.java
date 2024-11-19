package com.springboot.bankbackend.controller;

import com.springboot.bankbackend.bo.UpdateBalanceRequest;
import com.springboot.bankbackend.bo.UpdateBalanceResponse;
import com.springboot.bankbackend.bo.UpdateProfileRequest;
import com.springboot.bankbackend.bo.UserResponse;
import com.springboot.bankbackend.service.UserService;
import com.springboot.bankbackend.service.auth.CustomUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  // Update profile for logged in user
  @PutMapping("/update-profile")
  ResponseEntity<UserResponse> updateProfile(@RequestBody UpdateProfileRequest request) {
    UserResponse response = userService.updateProfile(request);

    // Check if the response is not null (indicating a successful update)
    if (response != null) {
      // Return a 201 Created status code along with some of the updated user data
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } else {
      // Handle the case where the update was not successful (e.g., validation failed)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  // Get profile for logged in user, optionally filter by date
  // The filterBefore and filterAfter parameters are optional
  // and must be in format of yyyy-MM-dd
  @GetMapping("/profile")
  ResponseEntity<UserResponse> getProfile(
      @RequestParam(required = false) String filterBefore,
      @RequestParam(required = false) String filterAfter) {
    UserResponse response = userService.getProfile(filterBefore, filterAfter);

    // Check if the response is not null (indicating a successful get)
    if (response != null) {
      // Return a 200 OK status code along with some of the user data
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      // Handle the case where the get was not successful (e.g., validation failed)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }

  // Make transactions for logged in user
  // The request body must be in format of UpdateBalanceRequest
  // Which is:
  // - toUserId: Long
  // - amount: Double
  // - transactionType: TransactionType
  // The transactionType must be one of the following: DEPOSIT, WITHDRAW, TRANSFER
  @PutMapping("/transactions")
  ResponseEntity<UpdateBalanceResponse> transaction(@RequestBody UpdateBalanceRequest request) {
    UpdateBalanceResponse response = userService.updateBalance(request);

    // Check if the response is not null (indicating a successful get)
    if (response != null) {
      // Return a 201 Created status code along with some of the user data
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } else {
      // Handle the case where the get was not successful (e.g., validation failed)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }
}
