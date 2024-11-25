package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.SavingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<SavingsEntity, Long> {
//    SavingsEntity delete();
}
