package com.example.server.dto;

import java.util.List;

public record VotesDto (
        Long user_id,
        Long room_id,
        List<Long> terms_id
) {
}
