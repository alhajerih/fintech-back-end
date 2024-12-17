package com.springboot.bankbackend.entity;

import javax.persistence.*;
@Table(name = "steps", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "daily_challenge_id"}),
        @UniqueConstraint(columnNames = {"user_id", "event_id"}),
        @UniqueConstraint(columnNames = {"user_id", "friend_challenge_id"})
})
@Entity
public class StepsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "daily_challenge_id")
    private DailyChallengeEntity dailyChallenge;

    @ManyToOne
    @JoinColumn(name = "friend_challenge_id")
    private FriendChallengeEntity friendChallenge;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;

    private Long steps;
    private Boolean completed = false;


    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public DailyChallengeEntity getDailyChallenge() {
        return dailyChallenge;
    }

    public void setDailyChallenge(DailyChallengeEntity dailyChallenge) {
        this.dailyChallenge = dailyChallenge;
    }

    public FriendChallengeEntity getFriendChallenge() {
        return friendChallenge;
    }

    public void setFriendChallenge(FriendChallengeEntity friendChallenge) {
        this.friendChallenge = friendChallenge;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public Long getSteps() {
        return steps;
    }

    public void setSteps(Long steps) {
        this.steps = steps;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
