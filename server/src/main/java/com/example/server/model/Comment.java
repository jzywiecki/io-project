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
public class Comment {
    /**
     * The id of the vote.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
    /**
     * The terms of the vote.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Term term;

    /**
     * The user who voted.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private User user;

    /**
     * The room where the vote was cast.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Room room;
}
