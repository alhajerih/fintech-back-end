package com.springboot.bankbackend.bo;

public class UserResponse {

  private Long id;
  private String username;
  private Double kilo;
  private String address;
  private Long totalSteps;
  private Integer weight;
  private Integer height;
private String role;
  //Constructor
  public UserResponse(Long id, String username, Double kilo, String address, Long totalSteps,Integer weight,Integer height,String role) {
    this.id = id;
    this.username = username;
    this.kilo = kilo;
    this.address = address;
    this.totalSteps = totalSteps;
    this.weight = weight;
    this.height = height;
    this.role=role;
  }


  // Setter and Getter


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

  public Double getKilo() {
    return kilo;
  }

  public void setKilo(Double kilo) {
    this.kilo = kilo;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Long getTotalSteps() {
    return totalSteps;
  }

  public void setTotalSteps(Long totalSteps) {
    this.totalSteps = totalSteps;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
