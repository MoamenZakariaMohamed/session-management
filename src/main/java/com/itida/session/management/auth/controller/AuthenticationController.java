package com.itida.session.management.auth.controller;

import com.itida.session.management.auth.domain.AuthRequest;
import com.itida.session.management.auth.domain.AuthResponse;
import com.itida.session.management.auth.domain.SignUpRequest;
import com.itida.session.management.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final AuthenticationService authService;

    /**
     * Register a new user
     * @param request SignUpRequest
     * @return AuthResponse
     */

    @PostMapping("/register")
    public AuthResponse register (
            @Valid @RequestBody SignUpRequest request
    ) {
        return authService.signUp(request);
    }

    /**
     * Login a user
     * @param request AuthRequest
     * @return AuthResponse
     */
    @PostMapping("/login")
    public AuthResponse login (
            @Valid @RequestBody AuthRequest request
    ) {
        return authService.login(request);
    }



}