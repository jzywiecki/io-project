package com.example.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Room not found.")
public class RoomNotFoundException extends RuntimeException {
    /**
     * Constructor.
     * @param message the message of exception.
     */

    public RoomNotFoundException(final String message) {
        super(message);
    }
}
