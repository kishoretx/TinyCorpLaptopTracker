package com.tinycorp.laptoptracker.repository;

import com.tinycorp.laptoptracker.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
