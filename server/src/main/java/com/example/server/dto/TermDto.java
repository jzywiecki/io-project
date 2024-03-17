package com.example.server.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record TermDto(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
}
