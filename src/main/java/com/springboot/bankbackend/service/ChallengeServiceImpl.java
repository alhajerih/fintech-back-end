package com.springboot.bankbackend.service;

import com.springboot.bankbackend.entity.DailyChallengeEntity;
import com.springboot.bankbackend.entity.FriendChallengeEntity;
import com.springboot.bankbackend.repository.DailyChallengeRepository;
import com.springboot.bankbackend.repository.FriendChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {
    @Autowired
    private DailyChallengeRepository dailyChallengeRepository;
    @Autowired
    private FriendChallengeRepository friendChallengeRepository;

    @Override
    public List<DailyChallengeEntity> getAllDailyChallenges() {
        return dailyChallengeRepository.findAll();
    }

    @Override
    public DailyChallengeEntity createDailyChallenge(DailyChallengeEntity challenge) {
        return dailyChallengeRepository.save(challenge);
    }

    @Override
    public List<FriendChallengeEntity> getAllFriendChallenges() {
        return friendChallengeRepository.findAll();
    }

    @Override
    public FriendChallengeEntity createFriendChallenge(FriendChallengeEntity challenge) {
        return friendChallengeRepository.save(challenge);
    }
}
