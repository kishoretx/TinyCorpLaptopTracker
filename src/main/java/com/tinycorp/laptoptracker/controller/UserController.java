package com.tinycorp.laptoptracker.controller;

import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.dto.user.CreateUserRequest;
import com.tinycorp.laptoptracker.dto.user.UserResponse;
import com.tinycorp.laptoptracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get users (paginated)")
    public ResponseEntity<PagedResponse<UserResponse>> getUsers(
            @Parameter(description = "0-based page number") @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") @Min(1) int size) {
        return ResponseEntity.ok(userService.getAllUsers(page, size));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping("/{userId}/disable")
    public ResponseEntity<UserResponse> disableUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.disableUser(userId));
    }
}
