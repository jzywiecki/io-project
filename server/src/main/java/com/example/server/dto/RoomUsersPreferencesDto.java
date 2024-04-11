package com.example.server.dto;

import java.util.Map;

public record RoomUsersPreferencesDto(
        Long roomId,
        Map<Long, UserPreferences> userPreferencesMap
) {
}
