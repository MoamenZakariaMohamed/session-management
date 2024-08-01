package com.itida.session.management.auth.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link AuthRequest}
 * @param email String that represents the email of the user
 * @param password String that represents the password of the user
 */
public record AuthRequest(
        @NotNull(message = "Email cannot be null")
        @NotBlank(message = "Email cannot be blank")
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Not well formatted Email")
        @Size(max =  50, message = "Email cannot be more than 50 characters")
         String email,
        @NotBlank(message = "password cannot be blank")
         String password
) {
}
