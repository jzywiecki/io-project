package com.example.server.model;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Vote {
    /**
     * The id of the vote.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The terms of the vote.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Term term;

    /**
     * The user who voted.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Users user;

    /**
     * The room where the vote was cast.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Room room;
}
