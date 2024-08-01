package com.itida.session.management.tracker.domain;

import com.itida.session.management.auth.domain.User;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * The interface Tracker repository.
 */
public interface TrackerRepository extends JpaRepository<Tracker, Long> {
    /**
     * Find all by date equals and and user page.
     *
     * @param date     the date
     * @param user     the user
     * @param pageable the pageable
     * @return the page
     */
    PageImpl<Tracker> findAllByDateEqualsAndAndUser(@Param("date") LocalDate date , @Param("user")User user, Pageable pageable);


}