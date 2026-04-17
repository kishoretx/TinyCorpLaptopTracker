package com.tinycorp.laptoptracker.dto.assignment;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class UpdateAssignmentRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long deviceId;

    @NotNull
    private LocalDate assignedDate;

    private LocalDate returnedDate;

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

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }
}
