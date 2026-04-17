package com.tinycorp.laptoptracker.service;

import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.dto.user.CreateUserRequest;
import com.tinycorp.laptoptracker.dto.user.UserResponse;

public interface UserService {
    PagedResponse<UserResponse> getAllUsers(int page, int size);

    UserResponse createUser(CreateUserRequest request);

    UserResponse disableUser(Long userId);
}
