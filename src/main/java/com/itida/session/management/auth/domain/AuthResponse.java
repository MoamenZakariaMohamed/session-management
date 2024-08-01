package com.itida.session.management.auth.domain;

/**
 * DTO for {@link AuthResponse}
 * @param accessToken JWT access token
 */
public record AuthResponse(
     String accessToken
) {
}
