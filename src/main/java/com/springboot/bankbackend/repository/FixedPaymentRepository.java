package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.FixedPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedPaymentRepository extends JpaRepository<FixedPaymentEntity, Long> {}
