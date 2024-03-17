package com.example.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Term not found.")
public class TermNotFoundException extends RuntimeException {
    public TermNotFoundException(String message) {
        super(message);
    }
}
