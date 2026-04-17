package com.tinycorp.laptoptracker.service.impl;

import com.tinycorp.laptoptracker.domain.Assignment;
import com.tinycorp.laptoptracker.domain.Device;
import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.dto.report.AverageDeviceAgeResponse;
import com.tinycorp.laptoptracker.dto.report.EolDeviceResponse;
import com.tinycorp.laptoptracker.dto.report.UserDevicesReportResponse;
import com.tinycorp.laptoptracker.repository.AssignmentRepository;
import com.tinycorp.laptoptracker.repository.DeviceRepository;
import com.tinycorp.laptoptracker.service.ReportService;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);
    private static final int APPROACHING_EOL_AGE = 4;
    private final DeviceRepository deviceRepository;
    private final AssignmentRepository assignmentRepository;

    public ReportServiceImpl(DeviceRepository deviceRepository, AssignmentRepository assignmentRepository) {
        this.deviceRepository = deviceRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public AverageDeviceAgeResponse getAverageDeviceAge() {
        log.info("Generating average device age report");
        List<Device> devices = deviceRepository.findAll();
        if (devices.isEmpty()) {
            return new AverageDeviceAgeResponse(0);
        }
        int currentYear = Year.now().getValue();
        double average = devices.stream()
                .mapToInt(device -> currentYear - device.getManufactureYear())
                .average()
                .orElse(0);
        return new AverageDeviceAgeResponse(average);
    }

    @Override
    public PagedResponse<UserDevicesReportResponse> getUsersWithAssignedDevices(int page, int size) {
        log.info("Generating users with assigned devices report page={}, size={}", page, size);
        List<Assignment> activeAssignments = assignmentRepository.findByReturnedDateIsNull();
        Map<Long, List<Assignment>> byUser = activeAssignments.stream()
                .collect(Collectors.groupingBy(a -> a.getUser().getId()));

        List<UserDevicesReportResponse> reportRows = byUser.values().stream()
                .map(assignments -> {
                    Assignment first = assignments.get(0);
                    List<UserDevicesReportResponse.DeviceSummary> devices = assignments.stream()
                            .map(a -> new UserDevicesReportResponse.DeviceSummary(
                                    a.getDevice().getId(),
                                    a.getDevice().getBrand(),
                                    a.getDevice().getModel()))
                            .toList();
                    return new UserDevicesReportResponse(first.getUser().getId(), first.getUser().getUsername(), devices);
                })
                .toList();

        return paginate(reportRows, page, size);
    }

    @Override
    public PagedResponse<EolDeviceResponse> getEolDevices(int page, int size) {
        log.info("Generating EOL devices report page={}, size={}", page, size);
        int currentYear = Year.now().getValue();
        List<EolDeviceResponse> reportRows = deviceRepository.findAll().stream()
                .map(device -> new EolDeviceResponse(
                        device.getId(),
                        device.getBrand(),
                        device.getModel(),
                        currentYear - device.getManufactureYear()))
                .filter(device -> device.getAge() >= APPROACHING_EOL_AGE)
                .toList();
        return paginate(reportRows, page, size);
    }

    private <T> PagedResponse<T> paginate(List<T> source, int page, int size) {
        int totalElements = source.size();
        int totalPages = size == 0 ? 0 : (int) Math.ceil((double) totalElements / size);
        int fromIndex = Math.min(page * size, totalElements);
        int toIndex = Math.min(fromIndex + size, totalElements);
        List<T> content = source.subList(fromIndex, toIndex);

        return new PagedResponse<>(
                content,
                page,
                size,
                totalElements,
                totalPages,
                page == 0,
                page >= Math.max(totalPages - 1, 0)
        );
    }
}
