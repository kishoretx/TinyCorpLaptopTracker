package com.tinycorp.laptoptracker.service;

import com.tinycorp.laptoptracker.dto.auth.LoginRequest;
import com.tinycorp.laptoptracker.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
