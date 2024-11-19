package com.springboot.bankbackend.bo;

import com.springboot.bankbackend.entity.TransactionEntity;
import java.util.List;

public class UserResponse {

  private Long id;

  private String username;

  private String role;

  private String phoneNumber;

  private String address;

  private String email;

  private Double balance;

  private Long bankId;

  private List<TransactionEntity> transactions;

  public UserResponse(
      Long id,
      String username,
      String role,
      String address,
      String phoneNumber,
      String email,
      Double balance,
      Long bankId,
      List<TransactionEntity> transactions) {
    this.id = id;
    this.username = username;
    this.role = role;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.email = email;
    this.balance = balance;
    this.bankId = bankId;
    this.transactions = transactions;
  }

  public List<TransactionEntity> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<TransactionEntity> transactions) {
    this.transactions = transactions;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public Long getBankId() {
    return bankId;
  }

  public void setBankId(Long bankId) {
    this.bankId = bankId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  //  public String getStatus() {
  //    return status;
  //  }
  //
  //  public void setStatus(String status) {
  //    this.status = status;
  //  }
}
