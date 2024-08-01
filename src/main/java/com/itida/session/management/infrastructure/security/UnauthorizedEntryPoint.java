package com.itida.session.management.infrastructure.security;

import com.itida.session.management.infrastructure.exception.ErrorModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * The type Unauthorized entry point.
 */
@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        final String expiredToken = (String) request.getAttribute("expired");
        final String message = expiredToken != null ? expiredToken : "Unauthorized access" ;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
         ErrorModel errorMsg = new ErrorModel(LocalDateTime.now() , HttpServletResponse.SC_UNAUTHORIZED, message , request.getRequestURI());
        response.getWriter().write(errorMsg.toString());
    }

}
