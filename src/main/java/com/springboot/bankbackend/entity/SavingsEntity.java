package com.springboot.bankbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class SavingsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long id;

  private String name;

  private Double amount;

  @Column(nullable = false)
  private Double currentAmount = 0.0;

  private Double amountAllocatedPerMonth;

  private Integer monthsUntilDeadline;

  private Long iconId;

  private String icon;

  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private UserEntity user;

  public SavingsEntity() {
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public Double getCurrentAmount() {
    return currentAmount;
  }

  public void setCurrentAmount(Double currentAmount) {
    this.currentAmount = currentAmount;
  }

  public Long getIconId() {
    return iconId;
  }

  public void setIconId(Long iconId) {
    this.iconId = iconId;
  }

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

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Double getAmountAllocatedPerMonth() {
    return amountAllocatedPerMonth;
  }

  public void setAmountAllocatedPerMonth(Double amountAllocatedPerMonth) {
    this.amountAllocatedPerMonth = amountAllocatedPerMonth;
  }

  public Integer getMonthsUntilDeadline() {
    return monthsUntilDeadline;
  }

  public void setMonthsUntilDeadline(Integer monthsUntilDeadline) {
    this.monthsUntilDeadline = monthsUntilDeadline;
  }
}
