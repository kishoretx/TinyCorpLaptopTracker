package com.tinycorp.laptoptracker.service.impl;

import com.tinycorp.laptoptracker.domain.Assignment;
import com.tinycorp.laptoptracker.domain.Device;
import com.tinycorp.laptoptracker.domain.User;
import com.tinycorp.laptoptracker.domain.enums.DeviceStatus;
import com.tinycorp.laptoptracker.domain.enums.UserStatus;
import com.tinycorp.laptoptracker.dto.assignment.AssignmentResponse;
import com.tinycorp.laptoptracker.dto.assignment.CreateAssignmentRequest;
import com.tinycorp.laptoptracker.dto.assignment.ReturnAssignmentRequest;
import com.tinycorp.laptoptracker.exception.BusinessException;
import com.tinycorp.laptoptracker.exception.ResourceNotFoundException;
import com.tinycorp.laptoptracker.repository.AssignmentRepository;
import com.tinycorp.laptoptracker.repository.DeviceRepository;
import com.tinycorp.laptoptracker.repository.UserRepository;
import com.tinycorp.laptoptracker.service.AssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private static final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, UserRepository userRepository,
                                 DeviceRepository deviceRepository) {
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    @Transactional
    public AssignmentResponse assignDevice(CreateAssignmentRequest request) {
        log.info("Assigning device id={} to user id={}", request.getDeviceId(), request.getUserId());
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Device device = deviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BusinessException("Cannot assign device to disabled user");
        }

        if (device.getStatus() != DeviceStatus.AVAILABLE) {
            throw new BusinessException("Device is not available for assignment");
        }

        assignmentRepository.findByDeviceAndReturnedDateIsNull(device).ifPresent(a -> {
            throw new BusinessException("Device already has an active assignment");
        });

        Assignment assignment = new Assignment();
        assignment.setUser(user);
        assignment.setDevice(device);
        assignment.setAssignedDate(request.getAssignedDate());
        Assignment saved = assignmentRepository.save(assignment);

        device.setStatus(DeviceStatus.ASSIGNED);
        deviceRepository.save(device);
        return MapperUtil.toAssignmentResponse(saved);
    }

    @Override
    @Transactional
    public AssignmentResponse returnDevice(Long assignmentId, ReturnAssignmentRequest request) {
        log.info("Returning assignment id={}", assignmentId);
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        if (assignment.getReturnedDate() != null) {
            throw new BusinessException("Assignment is already returned");
        }
        if (request.getReturnedDate().isBefore(assignment.getAssignedDate())) {
            throw new BusinessException("Returned date cannot be before assigned date");
        }

        assignment.setReturnedDate(request.getReturnedDate());
        Assignment saved = assignmentRepository.save(assignment);

        Device device = assignment.getDevice();
        if (device.getStatus() != DeviceStatus.RETIRED) {
            device.setStatus(DeviceStatus.AVAILABLE);
            deviceRepository.save(device);
        }
        return MapperUtil.toAssignmentResponse(saved);
    }
}
