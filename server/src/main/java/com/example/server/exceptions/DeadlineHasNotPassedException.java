package com.example.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,
        reason = "The deadline has not passed.")
public class DeadlineHasNotPassedException extends RuntimeException {
    /**
     * Constructor.
     * @param message the message of exception.
     */
    public DeadlineHasNotPassedException(final String message) {
        super(message);
    }
}
