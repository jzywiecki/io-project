package com.example.server.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record TermStringDto(
        long id, DayOfWeek day, String startTime, String endTime
) {
}
