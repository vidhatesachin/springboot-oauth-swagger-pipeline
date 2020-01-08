package com.medinosys.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationFailureException extends RuntimeException {
    public AuthenticationFailureException() {
        super();
    }
    public AuthenticationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
    public AuthenticationFailureException(String message) {
        super(message);
    }
    public AuthenticationFailureException(Throwable cause) {
        super(cause);
    }
}
