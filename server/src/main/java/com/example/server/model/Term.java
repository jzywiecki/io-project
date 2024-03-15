package com.example.server.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Term {
    @Id
    @GeneratedValue
    private Long id;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Room room;
}
