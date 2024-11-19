package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<BankAccountEntity, Long> {
        //    List<UserEntity> findByStatus(Status status);
        // Find by username, ignoring case
//        Optional<UserEntity> findByUsernameIgnoreCase(String username);
    }
