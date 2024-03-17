package com.example.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Result not found.")
public class ResultNotFoundException extends RuntimeException {
    /**
     * Constructor.
     * @param message the message of exception.
     */
    public ResultNotFoundException(final String message) {
        super(message);
    }
}
