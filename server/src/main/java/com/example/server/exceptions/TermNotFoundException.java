package com.example.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Term not found.")
public class TermNotFoundException extends RuntimeException {
    /**
     * Constructor.
     * @param message the message of exception.
     */
    public TermNotFoundException(final String message) {
        super(message);
    }
}
