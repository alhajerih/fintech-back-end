package com.springboot.bankbackend.bo;

public class ChallengeStatus {
    private Long dailyChallengeId;
    private String dailyChallengeName;
    private Long eventId;
    private String eventName;
    private Long friendChallengeId;
    private String friendChallengeName;
    private  Long steps;
    private Boolean completed;

    public ChallengeStatus(Long dailyChallengeId, String dailyChallengeName,
                           Long eventId, String eventName,
                           Long friendChallengeId, String friendChallengeName,
                           Boolean completed,Long steps) {
        this.dailyChallengeId = dailyChallengeId;
        this.dailyChallengeName = dailyChallengeName;
        this.eventId = eventId;
        this.eventName = eventName;
        this.friendChallengeId = friendChallengeId;
        this.friendChallengeName = friendChallengeName;
        this.completed = completed;
        this.steps =steps;
    }

    // Getters and Setters


    public Long getSteps() {
        return steps;
    }

    public void setSteps(Long steps) {
        this.steps = steps;
    }

    public Long getDailyChallengeId() {
        return dailyChallengeId;
    }

    public void setDailyChallengeId(Long dailyChallengeId) {
        this.dailyChallengeId = dailyChallengeId;
    }

    public String getDailyChallengeName() {
        return dailyChallengeName;
    }

    public void setDailyChallengeName(String dailyChallengeName) {
        this.dailyChallengeName = dailyChallengeName;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getFriendChallengeId() {
        return friendChallengeId;
    }

    public void setFriendChallengeId(Long friendChallengeId) {
        this.friendChallengeId = friendChallengeId;
    }

    public String getFriendChallengeName() {
        return friendChallengeName;
    }

    public void setFriendChallengeName(String friendChallengeName) {
        this.friendChallengeName = friendChallengeName;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
