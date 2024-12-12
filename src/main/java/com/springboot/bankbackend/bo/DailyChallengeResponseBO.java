package com.springboot.bankbackend.bo;

import com.springboot.bankbackend.entity.DailyChallengeEntity;

public class DailyChallengeResponseBO {
    private Long id;
    private Integer fixedPoints;
    private String dateTime;

    public DailyChallengeResponseBO(DailyChallengeEntity entity) {
        this.id = entity.getId();
        this.fixedPoints = entity.getFixedPoints();
        this.dateTime = entity.getDateTime();
    }

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
}
