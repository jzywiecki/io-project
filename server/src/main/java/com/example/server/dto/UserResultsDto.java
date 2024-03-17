package com.example.server.dto;

import com.example.server.model.Term;
import lombok.Builder;

@Builder
public record UserResultsDto(
    Term result,
    int totalVotes,
    long roomId,
    String roomName,
    String roomDescription
) {

}
