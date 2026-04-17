package com.tinycorp.laptoptracker.exception;

import java.time.Instant;
import java.util.List;

public class ApiError {
    private Instant timestamp;
    private int status;
    private String error;
    private List<String> details;

    public ApiError(Instant timestamp, int status, String error, List<String> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.details = details;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }
}
