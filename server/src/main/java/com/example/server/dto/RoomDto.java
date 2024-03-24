package com.example.server.dto;

import lombok.Builder;

import java.sql.Date;
import java.time.LocalTime;

@Builder
public record RoomDto(Long id,
                      String name,
                      String description,
                      Date deadlineDate,
                      LocalTime deadlineTime
) {
}
