package com.itida.session.management.auth.service;

import com.itida.session.management.auth.domain.AuthRequest;
import com.itida.session.management.auth.domain.AuthResponse;
import com.itida.session.management.auth.domain.SignUpRequest;
import com.itida.session.management.auth.domain.User;
import com.itida.session.management.auth.domain.UserRepository;
import com.itida.session.management.infrastructure.exception.ErrorMessages;
import com.itida.session.management.infrastructure.exception.GlobalBusinessException;
import com.itida.session.management.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImplementation implements AuthenticationService{
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;

    /**
     * Login user
     * @param request AuthRequest
     * @throws GlobalBusinessException if user not found
     * @return AuthResponse
     */
    @Override
    public AuthResponse login(AuthRequest request) {
        String email = request.email();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.password()
                )
        );
        var user = repository.findByEmail(email)
                .orElseThrow(()-> new GlobalBusinessException(HttpStatus.NOT_FOUND , ErrorMessages.USER_NOT_FOUND , email , email ));
        return new AuthResponse(jwtService.generateToken(user));
    }

    /**
     * Sign up user
     * @param request SignUpRequest
     */
    @Override
    public AuthResponse signUp(SignUpRequest request) {
        validateRequest(request);
        User user = repository.save(build(request));
        return new AuthResponse(jwtService.generateToken(user));
    }

    /**
     * Get current user
     * @return User entity
     */
    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    /**
     * Validate request
     * @param request SignUpRequest
     * @throws GlobalBusinessException if email or mobile number already exists
     */
    private void validateRequest(SignUpRequest request) {
        isValidEmail(request.email());
        isValidMobileNumber(request.mobileNumber());
    }

    /**
     * Check if email already exists
     * @throws GlobalBusinessException if email already exists
     * @param email String that represents email
     */
    private void isValidEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new GlobalBusinessException(HttpStatus.BAD_REQUEST,
                    ErrorMessages.EMAIL_DUPLICATE, email);
        }
    }

    /**
     * Check if mobile number already exists
     * @throws GlobalBusinessException if mobile number already exists
     * @param mobileNumber String that represents mobile number
     */
    private void isValidMobileNumber(String mobileNumber) {
        if (repository.existsByMobileNumber(mobileNumber)) {
            throw new GlobalBusinessException(HttpStatus.BAD_REQUEST,
                    ErrorMessages.MOBILE_NUMBER_DUPLICATE, mobileNumber);
        }
    }

    /**
     * Build user entity from request
     * @param request SignUpRequest
     * @return User entity
     */
    private User build(SignUpRequest request){
        return User.builder()
                .name(request.name())
                .email(request.email())
                .mobileNumber(request.mobileNumber())
                .password(passwordEncoder.encode(request.password()))
                .build();
    }
}
