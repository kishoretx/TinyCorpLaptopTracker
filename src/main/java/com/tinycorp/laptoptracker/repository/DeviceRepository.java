package com.tinycorp.laptoptracker.repository;

import com.tinycorp.laptoptracker.domain.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Page<Device> findByBrandContainingIgnoreCaseOrModelContainingIgnoreCaseOrCpuContainingIgnoreCase(
            String brand, String model, String cpu, Pageable pageable);
}
