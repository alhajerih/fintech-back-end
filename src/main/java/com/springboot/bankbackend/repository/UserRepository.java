package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // Find by username, ignoring case
    Optional<UserEntity> findByUsernameIgnoreCase(String username);
    boolean existsByUsernameIgnoreCase(String username);
    Optional<UserEntity> findByUsername(String username);
//    List<UserEntity> findByUsername(String username);

}
