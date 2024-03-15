package com.example.server.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private LocalTime deadline;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    @JoinTable(
            name = "room_users",
            joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    private Set<Users> joinedUsers;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private List<Term> terms;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private List<Vote> votes;
}
