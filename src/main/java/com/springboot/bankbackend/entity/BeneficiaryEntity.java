package com.springboot.bankbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class BeneficiaryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Boolean doesNeedGroceries;

  private Double groceriesMultiplier;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private UserEntity user;

  @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<FixedPaymentEntity> fixedPaymentList = new ArrayList<>();

  public BeneficiaryEntity() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getDoesNeedGroceries() {
    return doesNeedGroceries;
  }

  public void setDoesNeedGroceries(Boolean doesNeedGroceries) {
    this.doesNeedGroceries = doesNeedGroceries;
  }

  public Double getGroceriesMultiplier() {
    return groceriesMultiplier;
  }

  public void setGroceriesMultiplier(Double groceriesMultiplier) {
    this.groceriesMultiplier = groceriesMultiplier;
  }

  //    public List<FixedPaymentEntity> getFixedPayment() {
  //        return fixedPayment;
  //    }
  //
  //    public void setFixedPayment(List<FixedPaymentEntity> fixedPayment) {
  //        this.fixedPayment = fixedPayment;
  //    }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public List<FixedPaymentEntity> getFixedPaymentList() {
    return fixedPaymentList;
  }

  public void setFixedPaymentList(List<FixedPaymentEntity> fixedPaymentList) {
    this.fixedPaymentList = fixedPaymentList;
  }
}
