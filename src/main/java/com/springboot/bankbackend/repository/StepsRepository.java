package com.springboot.bankbackend.repository;

import com.springboot.bankbackend.entity.StepsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StepsRepository extends JpaRepository<StepsEntity,Long> {

    List<StepsEntity> findByUserId(Long userId);

    List<StepsEntity> findByUserIdAndDailyChallengeId(Long userId, Long dailyChallengeId);
    List<StepsEntity> findByUserIdAndFriendChallengeId(Long userId, Long friendChallengeId);
    List<StepsEntity> findByUserIdAndEventId(Long userId, Long eventId);

    @Query("SELECT SUM(s.steps) FROM StepsEntity s WHERE s.user.id = :userId")
    Long findTotalStepsByUserId(@Param("userId") Long userId);
}
