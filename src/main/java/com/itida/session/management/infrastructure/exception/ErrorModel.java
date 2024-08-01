package com.itida.session.management.infrastructure.exception;

import java.time.LocalDateTime;

/**
 * ErrorModel class represents the error details that will be returned in the response.
 * @param timestamp
 * @param status
 * @param message
 * @param path
 */
public record ErrorModel(
     LocalDateTime timestamp ,
     int status,
     String message,
     String path

) {
    public String toString() {
            return "{ \"timestamp\": \"" + timestamp + "\", \"status\": \"" + status + "\", \"message\": \"" + message + "\", \"path\": \"" + path + "\" }";
    }
}
