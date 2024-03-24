package com.example.server.dto;

import lombok.Builder;

@Builder
public record ResultDto(
    TermDto result,
    String firstName,
    String lastName,
    String email
) {
}
