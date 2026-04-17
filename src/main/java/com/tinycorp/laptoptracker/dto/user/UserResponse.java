package com.tinycorp.laptoptracker.dto.user;

import com.tinycorp.laptoptracker.domain.enums.RoleType;
import com.tinycorp.laptoptracker.domain.enums.UserStatus;
import java.time.Instant;

public class UserResponse {
    private Long id;
    private String username;
    private UserStatus status;
    private RoleType role;
    private Instant createdAt;

    public UserResponse() {
    }

    public UserResponse(Long id, String username, UserStatus status, RoleType role, Instant createdAt) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
