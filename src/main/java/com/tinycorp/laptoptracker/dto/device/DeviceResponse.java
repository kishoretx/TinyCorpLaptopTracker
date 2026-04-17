package com.tinycorp.laptoptracker.dto.device;

import com.tinycorp.laptoptracker.domain.enums.DeviceStatus;

public class DeviceResponse {
    private Long id;
    private String brand;
    private String model;
    private String cpu;
    private Integer ram;
    private Integer manufactureYear;
    private DeviceStatus status;

    public DeviceResponse() {
    }

    public DeviceResponse(Long id, String brand, String model, String cpu, Integer ram, Integer manufactureYear,
                          DeviceStatus status) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.cpu = cpu;
        this.ram = ram;
        this.manufactureYear = manufactureYear;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(Integer manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }
}
