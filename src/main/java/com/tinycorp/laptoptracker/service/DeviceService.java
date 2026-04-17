package com.tinycorp.laptoptracker.service;

import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.dto.device.CreateDeviceRequest;
import com.tinycorp.laptoptracker.dto.device.DeviceResponse;

public interface DeviceService {
    PagedResponse<DeviceResponse> getAllDevices(int page, int size);

    DeviceResponse getDeviceById(Long id);

    DeviceResponse createDevice(CreateDeviceRequest request);
}
