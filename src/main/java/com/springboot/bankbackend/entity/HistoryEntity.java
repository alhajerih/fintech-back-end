package com.springboot.bankbackend.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "historyEntity", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DailyChallengeEntity> dailyChallengeEntity = new ArrayList<>();

    @OneToMany(mappedBy = "historyEntity", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<FriendChallengeEntity> friendChallengeEntity = new ArrayList<>();

    @OneToMany(mappedBy = "historyEntity", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<EventEntity> eventEntity = new ArrayList<>();

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DailyChallengeEntity> getDailyChallengeEntity() {
        return dailyChallengeEntity;
    }

    public void setDailyChallengeEntity(List<DailyChallengeEntity> dailyChallengeEntity) {
        this.dailyChallengeEntity = dailyChallengeEntity;
    }

    public List<FriendChallengeEntity> getFriendChallengeEntity() {
        return friendChallengeEntity;
    }

    public void setFriendChallengeEntity(List<FriendChallengeEntity> friendChallengeEntity) {
        this.friendChallengeEntity = friendChallengeEntity;
    }

    public List<EventEntity> getEventEntity() {
        return eventEntity;
    }

    public void setEventEntity(List<EventEntity> eventEntity) {
        this.eventEntity = eventEntity;
    }
}

