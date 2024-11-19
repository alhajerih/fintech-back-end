package com.springboot.bankbackend.service.auth;

import com.springboot.bankbackend.bo.CustomUserDetails;
import com.springboot.bankbackend.entity.UserEntity;
import com.springboot.bankbackend.exception.UserNotFoundException;
import com.springboot.bankbackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return buildCustomUserDetailsOfUsername(username);
  }

  private CustomUserDetails buildCustomUserDetailsOfUsername(String username) {
    UserEntity user =
        userRepository
            .findByUsernameIgnoreCase(username)
            .orElseThrow(() -> new UserNotFoundException("Incorrect Username"));

    CustomUserDetails userDetails = new CustomUserDetails();
    userDetails.setId(user.getId());
    userDetails.setUserName(user.getUsername());
    userDetails.setPassword(user.getPassword());
    userDetails.setRole(user.getRole().toString());
    userDetails.setPhone(user.getPhoneNumber());
    userDetails.setEmail(user.getEmail());
    userDetails.setAddress(user.getAddress());
    userDetails.setTransactions(user.getTransactions());
    userDetails.setBankAccount(user.getBankAccount());

    return userDetails;
  }
}
