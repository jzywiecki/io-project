package com.example.server.dto;

import java.util.Map;
import java.util.Set;

public record RoomUsersPreferencesDto(
        Long roomId,
        Set<UserDataDto> users,
        Map<Long, UserPreferences> userPreferencesMap
) {
}
