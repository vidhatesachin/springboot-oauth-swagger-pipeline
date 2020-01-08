package com.springboot.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super();
    }
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidInputException(String message) {
        super(message);
    }
    public InvalidInputException(Throwable cause) {
        super(cause);
    }
}
