package com.springboot.bankbackend.bo;

public class DailyChallengeRequestBO {
    private Integer fixedPoints;
    private String dateTime;

    // Getters and Setters


    public DailyChallengeRequestBO() {
        // Default constructor
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
