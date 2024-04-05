package com.example.server.dto;

import java.util.List;
import java.util.Map;

public record VotingPageDto(
        List<TermDto> availableTerms,
        Map<Long, Boolean> termWithIdIsSelected,
        Map<Long, String> termWithIdComments
){
}
