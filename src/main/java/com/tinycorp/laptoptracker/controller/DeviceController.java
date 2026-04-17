package com.tinycorp.laptoptracker.controller;

import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.dto.device.CreateDeviceRequest;
import com.tinycorp.laptoptracker.dto.device.DeviceResponse;
import com.tinycorp.laptoptracker.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/devices")
@Validated
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    @Operation(summary = "Get devices (paginated)")
    public ResponseEntity<PagedResponse<DeviceResponse>> getDevices(
            @Parameter(description = "0-based page number") @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") @Min(1) int size) {
        return ResponseEntity.ok(deviceService.getAllDevices(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDevice(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(@Valid @RequestBody CreateDeviceRequest request) {
        return ResponseEntity.ok(deviceService.createDevice(request));
    }
}
