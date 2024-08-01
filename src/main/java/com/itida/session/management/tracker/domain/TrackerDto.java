package com.itida.session.management.tracker.domain;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * DTO for {@link Tracker}
 */
public record TrackerDto(
        @NotNull(message = "Date is required and cannot be null") LocalDate date,
        @NotNull(message = "Login time is required and cannot be null") LocalTime loginTime,
        @NotNull(message = "LogoutTime is required and cannot be null") LocalTime logoutTime){

    @AssertTrue(message = "The end time cannot be Before start time")
    private boolean isValidRequestedAppointment() {
        return loginTime.truncatedTo(ChronoUnit.MINUTES)
                .isBefore(logoutTime.truncatedTo(ChronoUnit.MINUTES));
    }}