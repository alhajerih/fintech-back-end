package com.springboot.bankbackend.bo;

public class EventRequestBO {
    private String dateTime;     // In string format (converted from LocalDateTime)
    private String locationName;
    private Integer fixedPoints;
    private String startTime;    // In string format (converted from LocalTime)
    private String endTime;      // In string format (converted from LocalTime)
    private String date;         // In string format (converted from LocalDate)

    // Getters and Setters


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getFixedPoints() {
        return fixedPoints;
    }

    public void setFixedPoints(Integer fixedPoints) {
        this.fixedPoints = fixedPoints;
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

