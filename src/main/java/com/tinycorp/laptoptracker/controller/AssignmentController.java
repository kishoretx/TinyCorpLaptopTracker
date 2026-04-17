package com.tinycorp.laptoptracker.controller;

import com.tinycorp.laptoptracker.dto.assignment.AssignmentResponse;
import com.tinycorp.laptoptracker.dto.assignment.CreateAssignmentRequest;
import com.tinycorp.laptoptracker.dto.assignment.ReturnAssignmentRequest;
import com.tinycorp.laptoptracker.dto.assignment.UpdateAssignmentRequest;
import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.service.AssignmentService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/assignments")
@Validated
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<AssignmentResponse>> getAssignments(
            @Parameter(description = "0-based page number") @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") @Min(1) int size,
            @Parameter(description = "Search by username/device brand/device model") @RequestParam(defaultValue = "") String q) {
        return ResponseEntity.ok(assignmentService.getAssignments(page, size, q));
    }

    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponse> getAssignment(@PathVariable Long assignmentId) {
        return ResponseEntity.ok(assignmentService.getAssignmentById(assignmentId));
    }

    @PostMapping
    public ResponseEntity<AssignmentResponse> assignDevice(@Valid @RequestBody CreateAssignmentRequest request) {
        return ResponseEntity.ok(assignmentService.assignDevice(request));
    }

    @PutMapping("/{assignmentId}")
    public ResponseEntity<AssignmentResponse> updateAssignment(@PathVariable Long assignmentId,
                                                               @Valid @RequestBody UpdateAssignmentRequest request) {
        return ResponseEntity.ok(assignmentService.updateAssignment(assignmentId, request));
    }

    @PutMapping("/{assignmentId}/return")
    public ResponseEntity<AssignmentResponse> returnDevice(@PathVariable Long assignmentId,
                                                           @Valid @RequestBody ReturnAssignmentRequest request) {
        return ResponseEntity.ok(assignmentService.returnDevice(assignmentId, request));
    }
}
