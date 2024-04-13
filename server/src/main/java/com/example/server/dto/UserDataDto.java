package com.example.server.dto;

public record UserDataDto (
        Long userId,
        String firstName,
        String lastName,
        String email
) {
}
