package com.springboot.bankbackend.service;

import com.springboot.bankbackend.bo.DailyChallengeRequestBO;
import com.springboot.bankbackend.bo.DailyChallengeResponseBO;
import com.springboot.bankbackend.bo.FriendChallengeRequestBO;
import com.springboot.bankbackend.bo.FriendChallengeResponseBO;
import com.springboot.bankbackend.entity.DailyChallengeEntity;
import com.springboot.bankbackend.entity.FriendChallengeEntity;
import com.springboot.bankbackend.entity.StepsEntity;
import com.springboot.bankbackend.entity.UserEntity;
import com.springboot.bankbackend.repository.DailyChallengeRepository;
import com.springboot.bankbackend.repository.FriendChallengeRepository;
import com.springboot.bankbackend.repository.StepsRepository;
import com.springboot.bankbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeServiceImpl implements ChallengeService {
    @Autowired
    private DailyChallengeRepository dailyChallengeRepository;
    @Autowired
    private FriendChallengeRepository friendChallengeRepository;

    @Autowired
    private StepsRepository stepsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<DailyChallengeResponseBO> getAllDailyChallenges() {
        return dailyChallengeRepository.findAll()
                .stream()
                .map(DailyChallengeResponseBO::new)
                .collect(Collectors.toList());
    }

    @Override
    public DailyChallengeResponseBO createDailyChallenge(DailyChallengeRequestBO dailyChallengeRequestBO) {
        DailyChallengeEntity challenge = new DailyChallengeEntity();
        challenge.setFixedPoints(dailyChallengeRequestBO.getFixedPoints());
        challenge.setDateTime(dailyChallengeRequestBO.getDateTime());
        DailyChallengeEntity savedChallenge = dailyChallengeRepository.save(challenge);
        return new DailyChallengeResponseBO(savedChallenge);
    }

    @Override
    public List<FriendChallengeResponseBO> getAllFriendChallenges() {
        return friendChallengeRepository.findAll()
                .stream()
                .map(challenge-> new FriendChallengeResponseBO(challenge.getId(),challenge.getName(),challenge.getStepGoal(),
                        challenge.getStartTime(),challenge.getEndTime(),challenge.getDate()))
                .collect(Collectors.toList());
    }

    @Override
    public FriendChallengeResponseBO createFriendChallenge(FriendChallengeRequestBO friendChallengeRequestBO) {
        // Create and populate the FriendChallengeEntity
        FriendChallengeEntity challenge = new FriendChallengeEntity();
        challenge.setStepGoal(friendChallengeRequestBO.getStepGoal());
        challenge.setStartTime(friendChallengeRequestBO.getStartTime());
        challenge.setEndTime(friendChallengeRequestBO.getEndTime());
        challenge.setDate(friendChallengeRequestBO.getDate());
        challenge.setName(friendChallengeRequestBO.getName());

        // Save the challenge entity to the database
        FriendChallengeEntity savedChallenge = friendChallengeRepository.save(challenge);

        // Use the FriendChallengeResponseBO constructor to map the data explicitly
        return new FriendChallengeResponseBO(
                savedChallenge.getId(),
                savedChallenge.getName(),
                savedChallenge.getStepGoal(),
                savedChallenge.getStartTime(),
                savedChallenge.getEndTime(),
                savedChallenge.getDate()
        );
    }







}

