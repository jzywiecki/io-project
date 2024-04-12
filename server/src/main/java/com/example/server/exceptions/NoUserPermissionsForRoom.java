package com.example.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User does not have permission to access the room.")
public class NoUserPermissionsForRoom extends RuntimeException{
}
