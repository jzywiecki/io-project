package com.example.server.dto;

import lombok.Builder;

import java.sql.Time;
import java.time.DayOfWeek;

@Builder
public record TermDto(
        DayOfWeek day,
        Time startTime,
        Time endTime
) {

}
