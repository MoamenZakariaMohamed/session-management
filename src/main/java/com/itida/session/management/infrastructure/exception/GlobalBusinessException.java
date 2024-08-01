package com.itida.session.management.infrastructure.exception;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;


@NoArgsConstructor
@Getter
@Setter
public class GlobalBusinessException extends RuntimeException{

    private String errorMessage;

    private String errorCode;

    private HttpStatus httpStatus;


    public GlobalBusinessException(HttpStatus httpStatus, ErrorMessages errorCode , Object...args) {
        super(format(errorCode.getValue(), args));
        this.errorMessage = format(errorCode.getValue(), args);
        this.errorCode = errorCode.toString();
        this.httpStatus = httpStatus;
    }

}
