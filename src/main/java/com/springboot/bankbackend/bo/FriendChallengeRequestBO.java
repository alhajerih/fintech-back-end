package com.springboot.bankbackend.bo;

public class FriendChallengeRequestBO {
    private Long stepGoal;
    private String startTime; // In string format (converted from LocalTime)
    private String endTime;   // In string format (converted from LocalTime)
    private String date;      // In string format (converted from LocalDate)

    // Getters and Setters


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
