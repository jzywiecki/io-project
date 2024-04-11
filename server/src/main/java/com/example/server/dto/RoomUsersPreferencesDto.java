package com.example.server.dto;

import java.util.Map;
import java.util.Set;

public record RoomUsersPreferencesDto(
        Long roomId,
        Set<UserDto> users,
        Map<Long, UserPreferences> userPreferencesMap
) {
}
