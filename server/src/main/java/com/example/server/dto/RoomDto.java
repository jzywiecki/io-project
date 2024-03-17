package com.example.server.dto;

import java.sql.Date;
import java.time.LocalTime;

public record RoomDto(String name, String description, Date deadlineDate, LocalTime deadlineTime) {
}