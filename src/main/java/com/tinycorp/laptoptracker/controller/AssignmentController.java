package com.tinycorp.laptoptracker.controller;

import com.tinycorp.laptoptracker.dto.assignment.AssignmentResponse;
import com.tinycorp.laptoptracker.dto.assignment.CreateAssignmentRequest;
import com.tinycorp.laptoptracker.dto.assignment.ReturnAssignmentRequest;
import com.tinycorp.laptoptracker.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<AssignmentResponse> assignDevice(@Valid @RequestBody CreateAssignmentRequest request) {
        return ResponseEntity.ok(assignmentService.assignDevice(request));
    }

    @PutMapping("/{assignmentId}/return")
    public ResponseEntity<AssignmentResponse> returnDevice(@PathVariable Long assignmentId,
                                                           @Valid @RequestBody ReturnAssignmentRequest request) {
        return ResponseEntity.ok(assignmentService.returnDevice(assignmentId, request));
    }
}
