package com.tinycorp.laptoptracker.config;

import com.tinycorp.laptoptracker.domain.Assignment;
import com.tinycorp.laptoptracker.domain.Device;
import com.tinycorp.laptoptracker.domain.User;
import com.tinycorp.laptoptracker.domain.enums.DeviceStatus;
import com.tinycorp.laptoptracker.domain.enums.RoleType;
import com.tinycorp.laptoptracker.domain.enums.UserStatus;
import com.tinycorp.laptoptracker.repository.AssignmentRepository;
import com.tinycorp.laptoptracker.repository.DeviceRepository;
import com.tinycorp.laptoptracker.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeederConfig {

    @Bean
    CommandLineRunner seedData(UserRepository userRepository, DeviceRepository deviceRepository,
                               AssignmentRepository assignmentRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                userRepository.saveAll(List.of(
                        buildUser("manager", "manager123", RoleType.MANAGER, UserStatus.ACTIVE),
                        buildUser("employee", "employee123", RoleType.EMPLOYEE, UserStatus.ACTIVE),
                        buildUser("alex", "alex123", RoleType.EMPLOYEE, UserStatus.ACTIVE),
                        buildUser("sam", "sam123", RoleType.EMPLOYEE, UserStatus.ACTIVE),
                        buildUser("lee", "lee123", RoleType.EMPLOYEE, UserStatus.DISABLED)
                ));
            }

            if (deviceRepository.count() == 0) {
                deviceRepository.saveAll(List.of(
                        buildDevice("Dell", "Latitude 7420", "Intel i7", 16, 2022, DeviceStatus.AVAILABLE),
                        buildDevice("Lenovo", "ThinkPad T14", "Intel i5", 16, 2021, DeviceStatus.AVAILABLE),
                        buildDevice("HP", "EliteBook 840", "Intel i7", 32, 2020, DeviceStatus.AVAILABLE),
                        buildDevice("Apple", "MacBook Pro 14", "M2 Pro", 16, 2023, DeviceStatus.AVAILABLE),
                        buildDevice("Acer", "TravelMate P2", "Intel i5", 8, 2019, DeviceStatus.AVAILABLE)
                ));
            }

            if (assignmentRepository.count() == 0 && userRepository.count() >= 2 && deviceRepository.count() >= 2) {
                User employee = userRepository.findByUsername("employee").orElseThrow();
                User alex = userRepository.findByUsername("alex").orElseThrow();

                Device deviceOne = deviceRepository.findAll().get(0);
                Device deviceTwo = deviceRepository.findAll().get(1);

                Assignment active = new Assignment();
                active.setUser(employee);
                active.setDevice(deviceOne);
                active.setAssignedDate(LocalDate.now().minusDays(15));
                assignmentRepository.save(active);
                deviceOne.setStatus(DeviceStatus.ASSIGNED);
                deviceRepository.save(deviceOne);

                Assignment closed = new Assignment();
                closed.setUser(alex);
                closed.setDevice(deviceTwo);
                closed.setAssignedDate(LocalDate.now().minusDays(40));
                closed.setReturnedDate(LocalDate.now().minusDays(10));
                assignmentRepository.save(closed);
                deviceTwo.setStatus(DeviceStatus.AVAILABLE);
                deviceRepository.save(deviceTwo);
            }
        };
    }

    private User buildUser(String username, String password, RoleType role, UserStatus status) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setStatus(status);
        return user;
    }

    private Device buildDevice(String brand, String model, String cpu, int ram, int manufactureYear, DeviceStatus status) {
        Device device = new Device();
        device.setBrand(brand);
        device.setModel(model);
        device.setCpu(cpu);
        device.setRam(ram);
        device.setManufactureYear(manufactureYear);
        device.setStatus(status);
        return device;
    }
}
