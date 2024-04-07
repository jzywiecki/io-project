package com.example.server.dto;

public record VoteDto(
        Long termId,
        Long userId,
        Long roomId
) {
}
