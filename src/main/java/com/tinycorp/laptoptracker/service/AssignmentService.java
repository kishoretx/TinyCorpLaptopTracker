package com.tinycorp.laptoptracker.service;

import com.tinycorp.laptoptracker.dto.assignment.AssignmentResponse;
import com.tinycorp.laptoptracker.dto.assignment.CreateAssignmentRequest;
import com.tinycorp.laptoptracker.dto.assignment.ReturnAssignmentRequest;
import com.tinycorp.laptoptracker.dto.assignment.UpdateAssignmentRequest;
import com.tinycorp.laptoptracker.dto.common.PagedResponse;

public interface AssignmentService {
    PagedResponse<AssignmentResponse> getAssignments(int page, int size, String search);

    AssignmentResponse getAssignmentById(Long assignmentId);

    AssignmentResponse assignDevice(CreateAssignmentRequest request);

    AssignmentResponse updateAssignment(Long assignmentId, UpdateAssignmentRequest request);

    AssignmentResponse returnDevice(Long assignmentId, ReturnAssignmentRequest request);
}
