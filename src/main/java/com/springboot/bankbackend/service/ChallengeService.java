package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.DailyChallengeRequestBO;
import com.springboot.bankbackend.bo.DailyChallengeResponseBO;
import com.springboot.bankbackend.bo.FriendChallengeRequestBO;
import com.springboot.bankbackend.bo.FriendChallengeResponseBO;
import com.springboot.bankbackend.entity.DailyChallengeEntity;
import com.springboot.bankbackend.entity.FriendChallengeEntity;

import java.util.List;

public interface ChallengeService {
    List<DailyChallengeResponseBO> getAllDailyChallenges();
    DailyChallengeResponseBO createDailyChallenge(DailyChallengeRequestBO dailyChallengeRequestBO);
    List<FriendChallengeResponseBO> getAllFriendChallenges();
    FriendChallengeResponseBO createFriendChallenge(FriendChallengeRequestBO friendChallengeRequestBO);
}

