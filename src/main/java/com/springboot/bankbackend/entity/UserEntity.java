package com.springboot.bankbackend.entity;

import jakarta.persistence.*;
import jakarta.transaction.Status;
import jdk.jshell.Snippet;
import com.springboot.bankbackend.utils.Roles;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String  email;
    private String  profilePicture;
    private List<String> beneficiaryAccountNumbers;

//    @Enumerated(EnumType.STRING)
//    private Status status;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String  password;

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
