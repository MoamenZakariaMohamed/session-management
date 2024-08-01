package com.itida.session.management.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<User> findByEmail(String email);

    /**
     * Exists by email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean existsByEmail(String email);

    /**
     * Exists by mobile number boolean.
     *
     * @param phoneNumber the phone number
     * @return the boolean
     */
    boolean existsByMobileNumber(String phoneNumber);
}