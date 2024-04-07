package com.example.server.dto;

import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Builder
public record TermDto(long id, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
}
