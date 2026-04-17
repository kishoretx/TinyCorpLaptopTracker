package com.tinycorp.laptoptracker.service.impl;

import com.tinycorp.laptoptracker.domain.Device;
import com.tinycorp.laptoptracker.domain.enums.DeviceStatus;
import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.dto.device.CreateDeviceRequest;
import com.tinycorp.laptoptracker.dto.device.DeviceResponse;
import com.tinycorp.laptoptracker.exception.ResourceNotFoundException;
import com.tinycorp.laptoptracker.repository.DeviceRepository;
import com.tinycorp.laptoptracker.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);
    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public PagedResponse<DeviceResponse> getAllDevices(int page, int size) {
        log.info("Fetching devices page={}, size={}", page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<DeviceResponse> devicesPage = deviceRepository.findAll(pageable).map(MapperUtil::toDeviceResponse);
        return new PagedResponse<>(
                devicesPage.getContent(),
                devicesPage.getNumber(),
                devicesPage.getSize(),
                devicesPage.getTotalElements(),
                devicesPage.getTotalPages(),
                devicesPage.isFirst(),
                devicesPage.isLast()
        );
    }

    @Override
    public DeviceResponse getDeviceById(Long id) {
        log.info("Fetching device id={}", id);
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        return MapperUtil.toDeviceResponse(device);
    }

    @Override
    public DeviceResponse createDevice(CreateDeviceRequest request) {
        log.info("Creating device brand={}, model={}", request.getBrand(), request.getModel());
        Device device = new Device();
        device.setBrand(request.getBrand());
        device.setModel(request.getModel());
        device.setCpu(request.getCpu());
        device.setRam(request.getRam());
        device.setManufactureYear(request.getManufactureYear());
        device.setStatus(DeviceStatus.AVAILABLE);
        Device saved = deviceRepository.save(device);
        return MapperUtil.toDeviceResponse(saved);
    }
}
