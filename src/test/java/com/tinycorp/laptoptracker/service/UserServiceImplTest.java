package com.tinycorp.laptoptracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tinycorp.laptoptracker.domain.User;
import com.tinycorp.laptoptracker.domain.enums.RoleType;
import com.tinycorp.laptoptracker.domain.enums.UserStatus;
import com.tinycorp.laptoptracker.dto.user.CreateUserRequest;
import com.tinycorp.laptoptracker.exception.BusinessException;
import com.tinycorp.laptoptracker.repository.UserRepository;
import com.tinycorp.laptoptracker.service.impl.UserServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldFailWhenUsernameExists() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("john");
        request.setPassword("pass");
        request.setRole(RoleType.EMPLOYEE);

        when(userRepository.existsByUsername("john")).thenReturn(true);

        assertThrows(BusinessException.class, () -> userService.createUser(request));
    }

    @Test
    void disableUser_shouldSetStatusToDisabled() {
        User user = new User();
        user.setId(1L);
        user.setStatus(UserStatus.ACTIVE);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        var response = userService.disableUser(1L);

        assertEquals(UserStatus.DISABLED, response.getStatus());
        verify(userRepository).save(user);
    }
}
