package com.tinycorp.laptoptracker.service;

import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.dto.device.CreateDeviceRequest;
import com.tinycorp.laptoptracker.dto.device.DeviceResponse;
import com.tinycorp.laptoptracker.dto.device.UpdateDeviceRequest;

public interface DeviceService {
    PagedResponse<DeviceResponse> getAllDevices(int page, int size, String search);

    DeviceResponse getDeviceById(Long id);

    DeviceResponse createDevice(CreateDeviceRequest request);

    DeviceResponse updateDevice(Long id, UpdateDeviceRequest request);
}
