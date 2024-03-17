package com.example.server.dto;

import java.sql.Time;
import java.time.DayOfWeek;

public record TermDto(DayOfWeek day, Time startTime, Time endTime) {
}
