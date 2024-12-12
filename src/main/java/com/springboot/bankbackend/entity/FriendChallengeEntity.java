package com.springboot.bankbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FriendChallengeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stepGoal;
    private Integer points;
    private Integer rank;
    private Long steps;
    private LocalTime startTime;
    private  LocalTime endTime;
    private LocalDate date;


    @ManyToMany
    @JoinTable(
            name = "challenge_opponents",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonManagedReference
    private List<UserEntity> opponents = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "history_id", nullable = false)
    private HistoryEntity historyEntity;

    // Setter and Getter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStepGoal() {
        return stepGoal;
    }

    public void setStepGoal(Long stepGoal) {
        this.stepGoal = stepGoal;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getSteps() {
        return steps;
    }

    public void setSteps(Long steps) {
        this.steps = steps;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<UserEntity> getOpponents() {
        return opponents;
    }

    public void setOpponents(List<UserEntity> opponents) {
        this.opponents = opponents;
    }

    public HistoryEntity getHistoryEntity() {
        return historyEntity;
    }

    public void setHistoryEntity(HistoryEntity historyEntity) {
        this.historyEntity = historyEntity;
    }
}
