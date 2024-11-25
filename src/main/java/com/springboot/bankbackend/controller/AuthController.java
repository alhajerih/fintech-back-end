package com.springboot.bankbackend.controller;

import com.springboot.bankbackend.bo.CreateUserRequest;
import com.springboot.bankbackend.bo.UserResponse;
import com.springboot.bankbackend.bo.auth.AuthenticationResponse;
import com.springboot.bankbackend.bo.auth.CreateLoginRequest;
import com.springboot.bankbackend.bo.auth.LogoutResponse;
import com.springboot.bankbackend.service.UserService;
import com.springboot.bankbackend.service.auth.AuthService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

  @Autowired private UserService userService;

  @Autowired private AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
    try {
      UserResponse response = userService.createUser(request);
      // Check if the response is not null (indicating a successful creation)
      if (response != null) {
        // Return a 201 Created status code along with the created user data
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
      } else {
        // Handle the case where the creation was not successful (e.g., validation failed)
        // You can return a different status code or error message as needed
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(
      @Valid @RequestBody CreateLoginRequest authenticationRequest) {
    try {
      AuthenticationResponse authenticationResponse = authService.login(authenticationRequest);
      HttpStatus status = HttpStatus.OK;

      if (authenticationResponse == null) {
        status = HttpStatus.BAD_REQUEST;
      }

      return new ResponseEntity<>(authenticationResponse, status);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@RequestBody LogoutResponse auntenticationRequset) {
    authService.logout(auntenticationRequset);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
