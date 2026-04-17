package com.tinycorp.laptoptracker.repository;

import com.tinycorp.laptoptracker.domain.Assignment;
import com.tinycorp.laptoptracker.domain.Device;
import com.tinycorp.laptoptracker.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByReturnedDateIsNull();

    List<Assignment> findByUserAndReturnedDateIsNull(User user);

    Optional<Assignment> findByDeviceAndReturnedDateIsNull(Device device);
}
