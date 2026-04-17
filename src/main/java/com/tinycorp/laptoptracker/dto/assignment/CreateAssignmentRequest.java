package com.tinycorp.laptoptracker.dto.assignment;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CreateAssignmentRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long deviceId;

    @NotNull
    private LocalDate assignedDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }
}
