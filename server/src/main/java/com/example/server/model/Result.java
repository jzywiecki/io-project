package com.example.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Result {
    /**
     * The id of the result.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The term selected by the algorithm.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Term term;

    /**
     * The user selected by the algorithm.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private User user;

    /**
     * The room where the vote was cast.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Room room;
}
