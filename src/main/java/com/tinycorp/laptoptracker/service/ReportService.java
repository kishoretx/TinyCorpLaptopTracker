package com.tinycorp.laptoptracker.service;

import com.tinycorp.laptoptracker.dto.common.PagedResponse;
import com.tinycorp.laptoptracker.dto.report.AverageDeviceAgeResponse;
import com.tinycorp.laptoptracker.dto.report.EolDeviceResponse;
import com.tinycorp.laptoptracker.dto.report.UserDevicesReportResponse;

public interface ReportService {
    AverageDeviceAgeResponse getAverageDeviceAge();

    PagedResponse<UserDevicesReportResponse> getUsersWithAssignedDevices(int page, int size);

    PagedResponse<EolDeviceResponse> getEolDevices(int page, int size);
}
