package com.springboot.bankbackend.service;

import com.springboot.bankbackend.entity.DailyChallengeEntity;
import com.springboot.bankbackend.entity.FriendChallengeEntity;

import java.util.List;

public interface ChallengeService {

    List<DailyChallengeEntity> getAllDailyChallenges();
    DailyChallengeEntity createDailyChallenge(DailyChallengeEntity challenge);
    List<FriendChallengeEntity> getAllFriendChallenges();
    FriendChallengeEntity createFriendChallenge(FriendChallengeEntity challenge);

}
