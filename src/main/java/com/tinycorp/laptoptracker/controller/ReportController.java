package com.tinycorp.laptoptracker.controller;

import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.dto.report.AverageDeviceAgeResponse;
import com.tinycorp.laptoptracker.dto.report.EolDeviceResponse;
import com.tinycorp.laptoptracker.dto.report.UserDevicesReportResponse;
import com.tinycorp.laptoptracker.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@Validated
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/average-device-age")
    public ResponseEntity<AverageDeviceAgeResponse> averageDeviceAge() {
        return ResponseEntity.ok(reportService.getAverageDeviceAge());
    }

    @GetMapping("/user-devices")
    @Operation(summary = "Get users with active assigned devices (paginated)")
    public ResponseEntity<PagedResponse<UserDevicesReportResponse>> userDevices(
            @Parameter(description = "0-based page number") @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") @Min(1) int size) {
        return ResponseEntity.ok(reportService.getUsersWithAssignedDevices(page, size));
    }

    @GetMapping("/eol-devices")
    @Operation(summary = "Get devices approaching EOL (age >= 4, paginated)")
    public ResponseEntity<PagedResponse<EolDeviceResponse>> eolDevices(
            @Parameter(description = "0-based page number") @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") @Min(1) int size) {
        return ResponseEntity.ok(reportService.getEolDevices(page, size));
    }
}
