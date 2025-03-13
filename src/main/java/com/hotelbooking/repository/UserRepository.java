package com.hotelbooking.repository;

import com.hotelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // Method to find users by their role (if needed for any specific queries)
    Optional<User> findByRole(String role);
}
