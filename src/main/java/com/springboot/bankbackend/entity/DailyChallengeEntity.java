package com.springboot.bankbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class DailyChallengeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer fixedPoints;

    @Column(name = "date_time")
    private String dateTime;

    @OneToMany(mappedBy = "dailyChallenge", cascade = CascadeType.ALL)
    private List<StepsEntity> steps;

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFixedPoints() {
        return fixedPoints;
    }

    public void setFixedPoints(Integer fixedPoints) {
        this.fixedPoints = fixedPoints;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<StepsEntity> getSteps() {
        return steps;
    }

    public void setSteps(List<StepsEntity> steps) {
        this.steps = steps;
    }
}
