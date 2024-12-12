package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity,Long> {
}
