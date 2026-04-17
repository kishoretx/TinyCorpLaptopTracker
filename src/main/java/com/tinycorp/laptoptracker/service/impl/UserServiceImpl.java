package com.tinycorp.laptoptracker.service.impl;

import com.tinycorp.laptoptracker.domain.User;
import com.tinycorp.laptoptracker.domain.enums.UserStatus;
import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.dto.user.CreateUserRequest;
import com.tinycorp.laptoptracker.dto.user.UpdateUserRequest;
import com.tinycorp.laptoptracker.dto.user.UserResponse;
import com.tinycorp.laptoptracker.exception.BusinessException;
import com.tinycorp.laptoptracker.exception.ResourceNotFoundException;
import com.tinycorp.laptoptracker.repository.UserRepository;
import com.tinycorp.laptoptracker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PagedResponse<UserResponse> getAllUsers(int page, int size, String search) {
        log.info("Fetching users page={}, size={}, search={}", page, size, search);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<UserResponse> usersPage;
        if (search == null || search.isBlank()) {
            usersPage = userRepository.findAll(pageable).map(MapperUtil::toUserResponse);
        } else {
            usersPage = userRepository.findByUsernameContainingIgnoreCase(search.trim(), pageable)
                    .map(MapperUtil::toUserResponse);
        }
        return new PagedResponse<>(
                usersPage.getContent(),
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                usersPage.isFirst(),
                usersPage.isLast()
        );
    }

    @Override
    public UserResponse getUserById(Long userId) {
        log.info("Fetching user id={}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return MapperUtil.toUserResponse(user);
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Creating user with username={}", request.getUsername());
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("Username already exists");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        user.setStatus(UserStatus.ACTIVE);
        User saved = userRepository.save(user);
        return MapperUtil.toUserResponse(saved);
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        log.info("Updating user id={}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (userRepository.existsByUsernameAndIdNot(request.getUsername(), userId)) {
            throw new BusinessException("Username already exists");
        }
        user.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(request.getPassword());
        }
        user.setRole(request.getRole());
        user.setStatus(request.getStatus());
        User saved = userRepository.save(user);
        return MapperUtil.toUserResponse(saved);
    }

    @Override
    public UserResponse disableUser(Long userId) {
        log.info("Disabling user id={}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getStatus() == UserStatus.DISABLED) {
            throw new BusinessException("User is already disabled");
        }
        user.setStatus(UserStatus.DISABLED);
        User saved = userRepository.save(user);
        return MapperUtil.toUserResponse(saved);
    }
}
