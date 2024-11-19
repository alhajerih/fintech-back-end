package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {}
