package com.example.server.dto;

import lombok.Builder;
import java.util.List;

@Builder
public record ResultsDto(
        List<ResultDto> results,
        int totalVotes,
        long roomId,
        String roomName,
        String roomDescription
) {
}
