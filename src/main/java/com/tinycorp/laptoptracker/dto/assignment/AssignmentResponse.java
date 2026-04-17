package com.tinycorp.laptoptracker.dto.assignment;

import java.time.LocalDate;

public class AssignmentResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long deviceId;
    private String deviceName;
    private LocalDate assignedDate;
    private LocalDate returnedDate;

    public AssignmentResponse() {
    }

    public AssignmentResponse(Long id, Long userId, String username, Long deviceId, String deviceName,
                              LocalDate assignedDate, LocalDate returnedDate) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.assignedDate = assignedDate;
        this.returnedDate = returnedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }
}
