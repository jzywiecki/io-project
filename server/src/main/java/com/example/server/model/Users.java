package com.example.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Users {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;

    private String lastName;

    // TODO: AGH email verification
    private String email;

    private String password;

    private Role role;

    private boolean active;

    @ManyToMany(mappedBy = "joinedUsers", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    @JsonBackReference
    private Set<Room> joinedRooms;

    @OneToMany(mappedBy = "user")
    private List<Vote> votes;
}
