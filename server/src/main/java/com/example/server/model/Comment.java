package com.example.server.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
     * The id of the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The content of the comment.
     */
    private String content;
    /**
     * The terms of the comment.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Term term;

    /**
     * The user who commented.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private User user;

    /**
     * The room where the comment was cast.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Room room;
}
