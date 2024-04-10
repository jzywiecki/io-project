package com.example.server.dto;

import java.util.List;

public record VotesDto(
        Long userId,
        Long roomId,
        List<Long> termsId
) {
}
