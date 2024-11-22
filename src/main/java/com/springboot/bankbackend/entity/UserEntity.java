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
    private Long Id;
    private String  email;
    private String  profilePicture;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @ElementCollection
    private List<String> beneficiaryAccountNumbers;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String  password;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<TransactionEntity> transactions = new ArrayList<>();

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(TransactionEntity transaction) {
        this.transactions.add(transaction);
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Roles getRole() {
        return role;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public List<String> getBeneficiaryAccountNumbers() {
        return beneficiaryAccountNumbers;
    }

    public void setBeneficiaryAccountNumbers(List<String> beneficiaryAccountNumbers) {
        this.beneficiaryAccountNumbers = beneficiaryAccountNumbers;
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
}
