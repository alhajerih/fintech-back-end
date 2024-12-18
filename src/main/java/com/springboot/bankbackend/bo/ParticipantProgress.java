package com.springboot.bankbackend.bo;

public class ParticipantProgress {
    private Long userId;
    private String username;
    private Long steps;
    private Long stepGoal;
    private Boolean completed;

    public ParticipantProgress(Long userId, String username, Long steps, Long stepGoal, Boolean completed) {
        this.userId = userId;
        this.username = username;
        this.steps = steps;
        this.stepGoal = stepGoal;
        this.completed = completed;
    }

    // Getters and Setters


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getSteps() {
        return steps;
    }

    public void setSteps(Long steps) {
        this.steps = steps;
    }

    public Long getStepGoal() {
        return stepGoal;
    }

    public void setStepGoal(Long stepGoal) {
        this.stepGoal = stepGoal;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}

