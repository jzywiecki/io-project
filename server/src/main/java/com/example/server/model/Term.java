package com.example.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Term {
    @Id
    @GeneratedValue
    private int id;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;

}
