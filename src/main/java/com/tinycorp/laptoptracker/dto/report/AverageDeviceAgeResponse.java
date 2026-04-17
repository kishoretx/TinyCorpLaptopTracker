package com.tinycorp.laptoptracker.dto.report;

public class AverageDeviceAgeResponse {
    private double averageAge;

    public AverageDeviceAgeResponse(double averageAge) {
        this.averageAge = averageAge;
    }

    public double getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(double averageAge) {
        this.averageAge = averageAge;
    }
}
