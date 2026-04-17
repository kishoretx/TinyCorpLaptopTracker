package com.tinycorp.laptoptracker.repository;

import com.tinycorp.laptoptracker.domain.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);

    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
