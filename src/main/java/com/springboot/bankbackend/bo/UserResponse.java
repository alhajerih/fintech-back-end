package com.springboot.bankbackend.bo;

public class UserResponse {

  private Long id;
  private String username;
  private Integer age;
  private String city;
  private Long totalSteps;
  private Integer weight;
  private Integer height;
private String role;
  //Constructor
  public UserResponse(Long id, String username, Integer age, String city, Long totalSteps,Integer weight,Integer height,String role) {
    this.id = id;
    this.username = username;
    this.age = age;
    this.city = city;
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

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
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
