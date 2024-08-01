package com.itida.session.management.auth.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link SignUpRequest}
 * @param name String that represents the name of the user
 * @param email String that represents the email of the user
 * @param password String that represents the password of the user
 * @param mobileNumber String that represents the mobile number of the user
 */
public record SignUpRequest(
         @NotBlank(message = "Name cannot be blank")
         @Size(max =  50, message = "Name cannot be more than 50 characters")
         String name,

         @NotBlank(message = "Email cannot be blank")
         @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                 message = "Not well formatted Email")
         @Size(max =  50, message = "Email cannot be more than 50 characters")
         String email,
         @NotBlank(message = "password cannot be blank")
         String password,

         @NotBlank(message = "Mobile number is required and cannot be blank")
         @Size(max = 11, message = "Mobile number cannot be more than 11 characters")
         @Pattern(regexp = "^01[0125]\\d{8}$",
                 message = "Not valid egyptian Mobile number format")
         String mobileNumber
) {
}
