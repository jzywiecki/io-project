package com.example.server.dto;

import java.util.List;

public record UserPreferences (
    List<Long> selectedTerms,
    List<CommentDto> comments
){
}
