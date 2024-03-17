package com.example.server.dto;

import java.sql.Date;
import java.sql.Time;

public record RoomDto(String name, String description, Date deadlinedate, Time deadlineTime) {
}
