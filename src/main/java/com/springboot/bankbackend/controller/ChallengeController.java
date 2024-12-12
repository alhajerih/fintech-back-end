package com.springboot.bankbackend.controller;

import com.springboot.bankbackend.bo.DailyChallengeRequestBO;
import com.springboot.bankbackend.bo.DailyChallengeResponseBO;
import com.springboot.bankbackend.bo.FriendChallengeRequestBO;
import com.springboot.bankbackend.bo.FriendChallengeResponseBO;
import com.springboot.bankbackend.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {
    @Autowired
    private ChallengeService challengeService;

    @GetMapping("/daily")
    public List<DailyChallengeResponseBO> getAllDailyChallenges() {
        return challengeService.getAllDailyChallenges();
    }

    @PostMapping("/daily")
    public DailyChallengeResponseBO createDailyChallenge(@RequestBody DailyChallengeRequestBO request) {
        return challengeService.createDailyChallenge(request);
    }

    @GetMapping("/friend")
    public List<FriendChallengeResponseBO> getAllFriendChallenges() {
        return challengeService.getAllFriendChallenges();
    }

    @PostMapping("/friend")
    public FriendChallengeResponseBO createFriendChallenge(@RequestBody FriendChallengeRequestBO request) {
        return challengeService.createFriendChallenge(request);
    }
}
