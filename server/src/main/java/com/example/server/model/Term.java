package com.example.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    /**
     * Term id.
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * Term day.
     */
    private DayOfWeek day;
    /**
     * Term start time.
     */
    private LocalTime startTime;
    /**
     * Term end time.
     */
    private LocalTime endTime;
    /**
     * Term room.
     */
    @ManyToMany(mappedBy = "terms", fetch = FetchType.EAGER,
            cascade = { CascadeType.PERSIST})
    @JsonBackReference
    private Set<Room> rooms;
    /**
     * Term votes.
     */
    @OneToMany(mappedBy = "term", fetch = FetchType.EAGER)
    private List<Vote> votes;
    /**
     * Represents the term that was selected in the algorithm.
     */
    @OneToMany(mappedBy = "term", fetch = FetchType.EAGER)
    private List<Result> results;
}
