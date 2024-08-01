package com.itida.session.management.infrastructure.exception;

import lombok.Getter;


/**
 * The enum Error messages.
 */
@Getter
public enum ErrorMessages {

    GLOBAL_MESSAGE_0001("Unexpected exception occurs due to %s"),
    USER_NOT_FOUND("No User found with that Email %s please SignUp first"),
    MOBILE_NUMBER_DUPLICATE("The mobile number %s is already registered. Please use a different number."),
    EMAIL_DUPLICATE("The email address %s is already in use. Please provide a different email."),
    TRACKER_001("Tracker not found with that provided id [%s]!");

    private final String value;

    ErrorMessages(String value) {
        this.value = value;
    }
}
