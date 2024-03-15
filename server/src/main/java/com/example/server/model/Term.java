package com.example.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "terms", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    @JsonBackReference
    private Set<Room> rooms;

    @OneToMany(mappedBy = "term", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private List<Vote> votes;
}
