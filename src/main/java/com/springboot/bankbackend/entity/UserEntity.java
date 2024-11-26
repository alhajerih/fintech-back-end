package com.springboot.bankbackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.springboot.bankbackend.utils.Roles;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  email;
    private String  profilePicture;
    private Long  favoriteSavingId;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String  password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TransactionEntity> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BeneficiaryEntity> beneficiaries = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SavingsEntity> savings = new ArrayList<>();

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(TransactionEntity transaction) {
        this.transactions.add(transaction);
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<BeneficiaryEntity> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<BeneficiaryEntity> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    public List<SavingsEntity> getSavings() {
        return savings;
    }

    public void setSavings(List<SavingsEntity> savings) {
        this.savings = savings;
    }

    public Long getFavoriteSavingId() { return favoriteSavingId;}

    public void setFavoriteSavingId(Long favoriteSavingId) {this.favoriteSavingId = favoriteSavingId;}

    public void addBeneficiary(BeneficiaryEntity beneficiary) {
        beneficiaries.add(beneficiary);
    }

    public void addSaving(SavingsEntity saving){ savings.add(saving); }
}
