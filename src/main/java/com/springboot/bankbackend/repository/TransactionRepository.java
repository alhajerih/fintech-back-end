package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionsEntity, Long> {}
