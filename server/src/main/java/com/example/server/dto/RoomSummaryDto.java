package com.example.server.dto;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

public record RoomSummaryDto(Long id,
                             String name,
                             String description,
                             String deadlineDate,
                             String deadlineTime,
                             List<TermDto> terms)  {
}
