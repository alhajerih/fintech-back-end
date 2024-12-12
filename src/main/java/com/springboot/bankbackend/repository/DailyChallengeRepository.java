package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.DailyChallengeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyChallengeRepository extends JpaRepository<DailyChallengeEntity,Long> {
}
