package com.tinycorp.laptoptracker.config;

import com.tinycorp.laptoptracker.domain.Device;
import com.tinycorp.laptoptracker.domain.User;
import com.tinycorp.laptoptracker.domain.enums.DeviceStatus;
import com.tinycorp.laptoptracker.domain.enums.RoleType;
import com.tinycorp.laptoptracker.domain.enums.UserStatus;
import com.tinycorp.laptoptracker.repository.DeviceRepository;
import com.tinycorp.laptoptracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeederConfig {

    @Bean
    CommandLineRunner seedData(UserRepository userRepository, DeviceRepository deviceRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User manager = new User();
                manager.setUsername("manager");
                manager.setPassword("manager123");
                manager.setRole(RoleType.MANAGER);
                manager.setStatus(UserStatus.ACTIVE);
                userRepository.save(manager);

                User employee = new User();
                employee.setUsername("employee");
                employee.setPassword("employee123");
                employee.setRole(RoleType.EMPLOYEE);
                employee.setStatus(UserStatus.ACTIVE);
                userRepository.save(employee);
            }

            if (deviceRepository.count() == 0) {
                Device one = new Device();
                one.setBrand("Dell");
                one.setModel("Latitude 7420");
                one.setCpu("Intel i7");
                one.setRam(16);
                one.setManufactureYear(2022);
                one.setStatus(DeviceStatus.AVAILABLE);
                deviceRepository.save(one);

                Device two = new Device();
                two.setBrand("Lenovo");
                two.setModel("ThinkPad T14");
                two.setCpu("Intel i5");
                two.setRam(16);
                two.setManufactureYear(2021);
                two.setStatus(DeviceStatus.AVAILABLE);
                deviceRepository.save(two);
            }
        };
    }
}
