package com.example.server.dto;

import java.util.Map;

public record RoomVotesDto(
        Long roomId,
        Map<Long, Integer> termVotesCount
) {
}
