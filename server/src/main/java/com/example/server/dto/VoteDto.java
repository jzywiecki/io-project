package com.example.server.dto;

public record VoteDto(
        Long term_id,
        Long user_id,
        Long room_id
) {
}
