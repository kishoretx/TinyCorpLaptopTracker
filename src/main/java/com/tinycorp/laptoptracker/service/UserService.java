package com.tinycorp.laptoptracker.service;

import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.dto.user.CreateUserRequest;
import com.tinycorp.laptoptracker.dto.user.UpdateUserRequest;
import com.tinycorp.laptoptracker.dto.user.UserResponse;

public interface UserService {
    PagedResponse<UserResponse> getAllUsers(int page, int size, String search);

    UserResponse getUserById(Long userId);

    UserResponse createUser(CreateUserRequest request);

    UserResponse updateUser(Long userId, UpdateUserRequest request);

    UserResponse disableUser(Long userId);
}
