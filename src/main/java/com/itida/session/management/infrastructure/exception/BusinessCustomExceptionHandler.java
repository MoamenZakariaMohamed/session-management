package com.itida.session.management.infrastructure.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Log4j2
public class BusinessCustomExceptionHandler{

    /**
     * Handle validation exception
     * @param ex MethodArgumentNotValidException
     * @param request  HttpServletRequest
     * @return ResponseEntity<ErrorModel>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorModel> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        errorMood(ex.getMessage());
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String msg = String.join(", ", errors);

        ErrorModel errorMessageModel = new ErrorModel(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                msg,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageModel);
    }

    /**
     * Handle business exception
     * @param e GlobalBusinessException
     * @param request HttpServletRequest
     * @return ResponseEntity<ErrorModel>
     */
    @ExceptionHandler(GlobalBusinessException.class)
    public ResponseEntity<ErrorModel> handleBusinessExceptionInterface(GlobalBusinessException e, HttpServletRequest request) {
        errorMood(e.getMessage());
        ErrorModel errorMessageModel= new ErrorModel (LocalDateTime.now(),
                e.getHttpStatus().value(),e.getErrorMessage(),request.getRequestURI());
        return ResponseEntity.status( e.getHttpStatus() != null ? e.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessageModel);

    }

    /**
     * Log error message
     * @param message Exception
     */
    private void errorMood(String message) {
            log.error("Exception: {} ",message);
    }
}
