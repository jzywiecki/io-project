package com.example.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Vote not found.")
public class VoteNotFoundException extends RuntimeException{
    public VoteNotFoundException(String message) {
        super(message);
    }
}
