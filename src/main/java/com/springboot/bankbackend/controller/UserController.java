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

  // Get profile for logged in user
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

  // Add defined transaction to user
  @PostMapping("/transactions")
  ResponseEntity<TransactionEntity> addTransaction(@Valid @RequestBody TransactionRequest request) {
    TransactionEntity response = userService.addTransaction(request);

    // Check if the response is not null (indicating a successful update)
    if (response != null) {
      // Return a 201 Created status code along with some of the updated user data
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } else {
      // Handle the case where the update was not successful (e.g., validation failed)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  // Get user transactions
  @GetMapping("/transactions")
  ResponseEntity<List<TransactionEntity>> getTransactions() {
    List<TransactionEntity> response = userService.getTransactions();

    // Check if the response is not null (indicating a successful get)
    if (response != null) {
      // Return a 200 OK status code along with some of the user data
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      // Handle the case where the get was not successful (e.g., validation failed)
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }

  @DeleteMapping("/transactions/{id}")
  public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
    userService.deleteTransaction(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/beneficiaries")
  public ResponseEntity<BeneficiaryEntity> addBeneficiary(@Valid @RequestBody BeneficiaryRequest request){
    BeneficiaryEntity beneficiary = userService.addBeneficiary(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(beneficiary);
  }

  @GetMapping("/beneficiaries")
  public ResponseEntity<List<BeneficiaryEntity>> getBeneficiaries(){
    List<BeneficiaryEntity> beneficiaries = userService.getBeneficiary();
    return ResponseEntity.ok(beneficiaries);
  }
  @DeleteMapping("/beneficiaries/{id}")
  public ResponseEntity<Void> deleteBeneficiary(@PathVariable Long id){
    userService.deleteBeneficiary(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/savings")
  public ResponseEntity<SavingsEntity> addSaving(@Valid @RequestBody SavingRequest request){
    SavingsEntity savings = userService.addSaving(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(savings);
  }

  @GetMapping("/savings")
  public ResponseEntity<List<SavingsEntity>> getSavings(){
    List<SavingsEntity> savings = userService.getSaving();
    return ResponseEntity.ok(savings);
  }

  @PutMapping("/savings/{id}")
  public ResponseEntity<SavingsEntity> updateSaving(@PathVariable Long id, @RequestBody Map<String, Double> requestBody){
    Double amountToAdd = requestBody.get("amountToAdd");

    if(amountToAdd == null) {
      return ResponseEntity.badRequest().body(null);
    }

    SavingsEntity updatedSaving = userService.updateSaving(id, amountToAdd);
    return ResponseEntity.ok(updatedSaving);
  }

  @DeleteMapping("/savings/{id}")
  public ResponseEntity<Void> deleteSaving(@PathVariable Long id) {
    userService.deleteSaving(id);
    return ResponseEntity.noContent().build();
  }

  // Fixed payments

  @PostMapping("savings/fixed-payments/{beneficiaryId}")
  public ResponseEntity<FixedPaymentEntity> addFixedPayment(@Valid @RequestBody FixedPaymentRequest request, @PathVariable Long beneficiaryId) {
    FixedPaymentEntity fixedPayment = userService.addFixedPayment(request, beneficiaryId);
    return ResponseEntity.status(HttpStatus.CREATED).body(fixedPayment);
  }

  @GetMapping("savings/fixed-payments/{beneficiaryId}")
  public ResponseEntity<List<FixedPaymentEntity>> getFixedPayments(@PathVariable Long beneficiaryId) {
    List<FixedPaymentEntity> fixedPayments = userService.getFixedPayment(beneficiaryId);
    return ResponseEntity.ok(fixedPayments);
  }

@DeleteMapping("savings/fixed-payments/{paymentId}")
  public ResponseEntity<Void> deleteFixedPayment(@PathVariable Long paymentId){
    userService.deleteFixedPayment(paymentId);
    return ResponseEntity.noContent().build();
}

@PostMapping("/savings/{savingId}")
public ResponseEntity<Void> addFavouriteSaving(@PathVariable Long savingId) {
    userService.addFavouriteSaving(savingId);
    return ResponseEntity.noContent().build();
}

@PutMapping("/savings/icon/{savingId}")
  public ResponseEntity<SavingsEntity> addFavouriteIcon(@RequestBody FavoriteSavingRequest request, @PathVariable Long savingId){
    SavingsEntity updateSaving = userService.addFavouriteIcon(request);
    return ResponseEntity.ok(updateSaving);
}

}
