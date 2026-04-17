package com.tinycorp.laptoptracker.service.impl;

import com.tinycorp.laptoptracker.domain.Assignment;
import com.tinycorp.laptoptracker.domain.Device;
import com.tinycorp.laptoptracker.domain.User;
import com.tinycorp.laptoptracker.dto.assignment.AssignmentResponse;
import com.tinycorp.laptoptracker.dto.device.DeviceResponse;
import com.tinycorp.laptoptracker.dto.user.UserResponse;

public final class MapperUtil {

    private MapperUtil() {
    }

    static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getStatus(), user.getRole(), user.getCreatedAt());
    }

    static DeviceResponse toDeviceResponse(Device device) {
        return new DeviceResponse(
                device.getId(),
                device.getBrand(),
                device.getModel(),
                device.getCpu(),
                device.getRam(),
                device.getManufactureYear(),
                device.getStatus()
        );
    }

    static AssignmentResponse toAssignmentResponse(Assignment assignment) {
        return new AssignmentResponse(
                assignment.getId(),
                assignment.getUser().getId(),
                assignment.getUser().getUsername(),
                assignment.getDevice().getId(),
                assignment.getDevice().getBrand() + " " + assignment.getDevice().getModel(),
                assignment.getAssignedDate(),
                assignment.getReturnedDate()
        );
    }
}
