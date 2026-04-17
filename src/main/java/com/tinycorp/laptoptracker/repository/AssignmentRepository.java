package com.tinycorp.laptoptracker.repository;

import com.tinycorp.laptoptracker.domain.Assignment;
import com.tinycorp.laptoptracker.domain.Device;
import com.tinycorp.laptoptracker.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByReturnedDateIsNull();

    List<Assignment> findByUserAndReturnedDateIsNull(User user);

    Optional<Assignment> findByDeviceAndReturnedDateIsNull(Device device);

    Optional<Assignment> findByDeviceAndReturnedDateIsNullAndIdNot(Device device, Long id);

    @Override
    @EntityGraph(attributePaths = {"user", "device"})
    Page<Assignment> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user", "device"})
    @Query("""
            select a from Assignment a
            join a.user u
            join a.device d
            where lower(u.username) like lower(concat('%', :q, '%'))
               or lower(d.brand) like lower(concat('%', :q, '%'))
               or lower(d.model) like lower(concat('%', :q, '%'))
            """)
    Page<Assignment> search(@Param("q") String q, Pageable pageable);
}
