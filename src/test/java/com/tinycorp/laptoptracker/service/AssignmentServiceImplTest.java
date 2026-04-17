package com.tinycorp.laptoptracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tinycorp.laptoptracker.domain.Assignment;
import com.tinycorp.laptoptracker.domain.Device;
import com.tinycorp.laptoptracker.domain.User;
import com.tinycorp.laptoptracker.domain.enums.DeviceStatus;
import com.tinycorp.laptoptracker.domain.enums.UserStatus;
import com.tinycorp.laptoptracker.dto.assignment.CreateAssignmentRequest;
import com.tinycorp.laptoptracker.exception.BusinessException;
import com.tinycorp.laptoptracker.repository.AssignmentRepository;
import com.tinycorp.laptoptracker.repository.DeviceRepository;
import com.tinycorp.laptoptracker.repository.UserRepository;
import com.tinycorp.laptoptracker.service.impl.AssignmentServiceImpl;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceImplTest {

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    @Test
    void assignDevice_shouldFailWhenUserDisabled() {
        User user = new User();
        user.setId(1L);
        user.setStatus(UserStatus.DISABLED);

        Device device = new Device();
        device.setId(2L);
        device.setStatus(DeviceStatus.AVAILABLE);

        CreateAssignmentRequest request = new CreateAssignmentRequest();
        request.setUserId(1L);
        request.setDeviceId(2L);
        request.setAssignedDate(LocalDate.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(deviceRepository.findById(2L)).thenReturn(Optional.of(device));

        assertThrows(BusinessException.class, () -> assignmentService.assignDevice(request));
        verify(assignmentRepository, never()).save(org.mockito.ArgumentMatchers.any(Assignment.class));
    }

    @Test
    void assignDevice_shouldUpdateDeviceStatusToAssigned() {
        User user = new User();
        user.setId(1L);
        user.setUsername("employee");
        user.setStatus(UserStatus.ACTIVE);

        Device device = new Device();
        device.setId(2L);
        device.setBrand("Dell");
        device.setModel("XPS");
        device.setStatus(DeviceStatus.AVAILABLE);

        CreateAssignmentRequest request = new CreateAssignmentRequest();
        request.setUserId(1L);
        request.setDeviceId(2L);
        request.setAssignedDate(LocalDate.now());

        Assignment saved = new Assignment();
        saved.setId(10L);
        saved.setUser(user);
        saved.setDevice(device);
        saved.setAssignedDate(request.getAssignedDate());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(deviceRepository.findById(2L)).thenReturn(Optional.of(device));
        when(assignmentRepository.findByDeviceAndReturnedDateIsNull(device)).thenReturn(Optional.empty());
        when(assignmentRepository.save(org.mockito.ArgumentMatchers.any(Assignment.class))).thenReturn(saved);

        var response = assignmentService.assignDevice(request);

        assertEquals(10L, response.getId());
        assertEquals(DeviceStatus.ASSIGNED, device.getStatus());
        verify(deviceRepository).save(device);
    }
}
