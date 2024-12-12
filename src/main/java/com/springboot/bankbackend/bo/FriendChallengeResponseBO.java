package com.springboot.bankbackend.bo;

public class FriendChallengeResponseBO {
    private Long id;
    private Long stepGoal;
    private String startTime; // In string format (converted from LocalTime)
    private String endTime;   // In string format (converted from LocalTime)
    private String date;      // In string format (converted from LocalDate)

    public FriendChallengeResponseBO(Long id, Long stepGoal, String startTime, String endTime, String date) {
        this.id = id;
        this.stepGoal = stepGoal;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

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
}
