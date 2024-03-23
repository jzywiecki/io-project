package com.example.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
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
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE})
    private Term term;

    /**
     * The user selected by the algorithm.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE})
    private User user;
    /**
     * The room where the vote was cast.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE})
    private Room room;
}
