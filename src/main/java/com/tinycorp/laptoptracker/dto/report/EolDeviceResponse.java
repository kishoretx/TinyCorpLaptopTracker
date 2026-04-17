package com.tinycorp.laptoptracker.dto.report;

public class EolDeviceResponse {
    private Long deviceId;
    private String brand;
    private String model;
    private int age;

    public EolDeviceResponse(Long deviceId, String brand, String model, int age) {
        this.deviceId = deviceId;
        this.brand = brand;
        this.model = model;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
