package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity,Long> {
}
