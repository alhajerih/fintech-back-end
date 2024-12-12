package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.StepsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepsRepository extends JpaRepository<StepsEntity,Long> {

    List<StepsEntity> findByUserId(Long userId);

}
