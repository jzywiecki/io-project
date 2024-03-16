package com.example.server.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {
    /**
     *  Room id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     *  Room name.
     */
    private String name;
    /**
     *  Room description.
     */
    private String description;
    /**
     *  Room deadline.
     */
    private LocalDateTime deadline;
    /**
     *  Room owner.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    @JoinTable(
            name = "room_user",
            joinColumns = @JoinColumn(name = "room_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id")
    )
    @JsonManagedReference
    private Set<User> joinedUsers;
    /**
     *  Room terms.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    @JoinTable(
            name = "room_term",
            joinColumns = @JoinColumn(name = "room_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "term_id",
                    referencedColumnName = "id")
    )
    @JsonManagedReference
    private Set<Term> terms;
    /**
     *  Room votes.
     */
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST})
    private List<Vote> votes;

    /**
     *  Represents the results of the algorithm for the room after stop voting.
     */
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST})
    private List<Result> results;
}
