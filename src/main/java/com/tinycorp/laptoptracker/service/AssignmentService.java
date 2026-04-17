package com.tinycorp.laptoptracker.service;

import com.tinycorp.laptoptracker.dto.assignment.AssignmentResponse;
import com.tinycorp.laptoptracker.dto.assignment.CreateAssignmentRequest;
import com.tinycorp.laptoptracker.dto.assignment.ReturnAssignmentRequest;

public interface AssignmentService {
    AssignmentResponse assignDevice(CreateAssignmentRequest request);

    AssignmentResponse returnDevice(Long assignmentId, ReturnAssignmentRequest request);
}
