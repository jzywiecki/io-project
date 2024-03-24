package com.example.server.dto;

import lombok.Builder;

@Builder
public record UserResultsDto(
    TermDto result,
    int totalVotes,
    long roomId,
    String roomName,
    String roomDescription
) {

}
