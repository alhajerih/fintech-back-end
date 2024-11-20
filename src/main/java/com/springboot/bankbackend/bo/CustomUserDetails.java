package com.springboot.bankbackend.bo;

import com.springboot.bankbackend.entity.BankAccountEntity;
import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

  private Long id;
  private String userName;
  private String password;
  private String email;
  private String role;
  private List<BankAccountEntity> bankAccounts;

  public List<BankAccountEntity> getBankAccounts() {
    return bankAccounts;
  }

  public void setBankAccounts(List<BankAccountEntity> bankAccount) {
    if (this.bankAccounts == null) {
      this.bankAccounts = new ArrayList<>();
    }
    this.bankAccounts = bankAccount;
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority(role));
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    return userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public Map<String, Object> getClaims() {
    HashMap<String, Object> claims = new HashMap<>();

    claims.put("id", this.id);
    claims.put("userName", this.userName);
    claims.put("role", role);

    return claims;
  }
}
