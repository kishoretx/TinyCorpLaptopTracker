package com.tinycorp.laptoptracker.dto.device;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateDeviceRequest {

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String cpu;

    @NotNull
    @Min(1)
    @Max(1024)
    private Integer ram;

    @NotNull
    @Min(1990)
    @Max(2100)
    private Integer manufactureYear;

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
}
