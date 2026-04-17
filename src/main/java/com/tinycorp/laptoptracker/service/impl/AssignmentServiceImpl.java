package com.tinycorp.laptoptracker.service.impl;

import com.tinycorp.laptoptracker.domain.Assignment;
import com.tinycorp.laptoptracker.domain.Device;
import com.tinycorp.laptoptracker.domain.User;
import com.tinycorp.laptoptracker.domain.enums.DeviceStatus;
import com.tinycorp.laptoptracker.domain.enums.UserStatus;
import com.tinycorp.laptoptracker.dto.assignment.AssignmentResponse;
import com.tinycorp.laptoptracker.dto.assignment.CreateAssignmentRequest;
import com.tinycorp.laptoptracker.dto.assignment.ReturnAssignmentRequest;
import com.tinycorp.laptoptracker.dto.assignment.UpdateAssignmentRequest;
import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.exception.BusinessException;
import com.tinycorp.laptoptracker.exception.ResourceNotFoundException;
import com.tinycorp.laptoptracker.repository.AssignmentRepository;
import com.tinycorp.laptoptracker.repository.DeviceRepository;
import com.tinycorp.laptoptracker.repository.UserRepository;
import com.tinycorp.laptoptracker.service.AssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Transactional(readOnly = true)
    public PagedResponse<AssignmentResponse> getAssignments(int page, int size, String search) {
        log.info("Fetching assignments page={}, size={}, search={}", page, size, search);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<AssignmentResponse> assignmentPage;
        if (search == null || search.isBlank()) {
            assignmentPage = assignmentRepository.findAll(pageable).map(MapperUtil::toAssignmentResponse);
        } else {
            assignmentPage = assignmentRepository.search(search.trim(), pageable).map(MapperUtil::toAssignmentResponse);
        }
        return new PagedResponse<>(
                assignmentPage.getContent(),
                assignmentPage.getNumber(),
                assignmentPage.getSize(),
                assignmentPage.getTotalElements(),
                assignmentPage.getTotalPages(),
                assignmentPage.isFirst(),
                assignmentPage.isLast()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AssignmentResponse getAssignmentById(Long assignmentId) {
        log.info("Fetching assignment id={}", assignmentId);
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));
        return MapperUtil.toAssignmentResponse(assignment);
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
    public AssignmentResponse updateAssignment(Long assignmentId, UpdateAssignmentRequest request) {
        log.info("Updating assignment id={}", assignmentId);
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new BusinessException("Cannot assign device to disabled user");
        }

        Device currentDevice = assignment.getDevice();
        Device targetDevice = deviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        if (request.getReturnedDate() != null && request.getReturnedDate().isBefore(request.getAssignedDate())) {
            throw new BusinessException("Returned date cannot be before assigned date");
        }

        if (request.getReturnedDate() == null) {
            if (targetDevice.getStatus() != DeviceStatus.AVAILABLE && !targetDevice.getId().equals(currentDevice.getId())) {
                throw new BusinessException("Device is not available for assignment");
            }
            assignmentRepository.findByDeviceAndReturnedDateIsNullAndIdNot(targetDevice, assignmentId).ifPresent(a -> {
                throw new BusinessException("Device already has an active assignment");
            });
        }

        assignment.setUser(user);
        assignment.setDevice(targetDevice);
        assignment.setAssignedDate(request.getAssignedDate());
        assignment.setReturnedDate(request.getReturnedDate());
        Assignment saved = assignmentRepository.save(assignment);

        updateDeviceStatesAfterAssignmentUpdate(currentDevice, targetDevice, request.getReturnedDate() == null);
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

    private void updateDeviceStatesAfterAssignmentUpdate(Device oldDevice, Device newDevice, boolean activeAssignment) {
        if (!oldDevice.getId().equals(newDevice.getId())) {
            if (oldDevice.getStatus() != DeviceStatus.RETIRED) {
                oldDevice.setStatus(DeviceStatus.AVAILABLE);
                deviceRepository.save(oldDevice);
            }
        }
        if (newDevice.getStatus() != DeviceStatus.RETIRED) {
            newDevice.setStatus(activeAssignment ? DeviceStatus.ASSIGNED : DeviceStatus.AVAILABLE);
            deviceRepository.save(newDevice);
        }
    }
}
