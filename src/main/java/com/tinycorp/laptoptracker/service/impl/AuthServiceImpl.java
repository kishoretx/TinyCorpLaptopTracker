package com.tinycorp.laptoptracker.service.impl;

import com.tinycorp.laptoptracker.domain.User;
import com.tinycorp.laptoptracker.domain.enums.UserStatus;
import com.tinycorp.laptoptracker.dto.auth.LoginRequest;
import com.tinycorp.laptoptracker.dto.auth.LoginResponse;
import com.tinycorp.laptoptracker.exception.UnauthorizedException;
import com.tinycorp.laptoptracker.repository.UserRepository;
import com.tinycorp.laptoptracker.security.JwtService;
import com.tinycorp.laptoptracker.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("Authenticating user username={}", request.getUsername());
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        if (!user.getPassword().equals(request.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new UnauthorizedException("User is disabled");
        }
        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }
}
