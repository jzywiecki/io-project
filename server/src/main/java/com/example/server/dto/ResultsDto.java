package com.example.server.dto;

import com.example.server.model.Term;
import com.example.server.model.User;
import lombok.Builder;

import java.util.Map;

@Builder
public record ResultsDto(
        Map<User, Term> results,
        int totalVotes,
        long roomId,
        String roomName,
        String roomDescription
) {
}
