package com.springboot.bankbackend.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class FriendChallengeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stepGoal;

    private String name;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "date")
    private String date;

    @OneToMany(mappedBy = "friendChallenge", cascade = CascadeType.ALL)
    private List<StepsEntity> steps;

    // Getters and Setters


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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StepsEntity> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsEntity> steps) {
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
