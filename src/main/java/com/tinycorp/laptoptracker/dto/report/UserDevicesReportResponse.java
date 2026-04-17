package com.tinycorp.laptoptracker.dto.report;

import java.util.List;

public class UserDevicesReportResponse {
    private Long userId;
    private String username;
    private List<DeviceSummary> devices;

    public UserDevicesReportResponse(Long userId, String username, List<DeviceSummary> devices) {
        this.userId = userId;
        this.username = username;
        this.devices = devices;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<DeviceSummary> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceSummary> devices) {
        this.devices = devices;
    }

    public static class DeviceSummary {
        private Long deviceId;
        private String brand;
        private String model;

        public DeviceSummary(Long deviceId, String brand, String model) {
            this.deviceId = deviceId;
            this.brand = brand;
            this.model = model;
        }

        public Long getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Long deviceId) {
            this.deviceId = deviceId;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }
}
