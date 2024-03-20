package com.example.server.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public record RoomDto(
        long id,
        String name,
        String description,
        LocalDate deadlineDate,
        Time deadlineTime
) {

}
