package com.example.server.dto;

public record UserDto (
    Long userId,
    String firstName,
    String lastName,
    String email
) {
}
