package com.itida.session.management.auth.service;

import com.itida.session.management.auth.domain.AuthRequest;
import com.itida.session.management.auth.domain.AuthResponse;
import com.itida.session.management.auth.domain.SignUpRequest;
import com.itida.session.management.auth.domain.User;

/**
 * The interface Authentication service.
 */
public interface AuthenticationService {
    /**
     * Login auth response.
     *
     * @param request the request
     * @return the auth response
     */
    AuthResponse login(AuthRequest request);

    /**
     * Sign up auth response.
     *
     * @param request the request
     * @return the auth response
     */
    AuthResponse signUp(SignUpRequest request);

    /**
     * Gets current user.
     *
     * @return the current user
     */
    User getCurrentUser();
}
