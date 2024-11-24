package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.BeneficiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaryRepository extends JpaRepository<BeneficiaryEntity, Long> {

}
