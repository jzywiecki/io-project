package com.example.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

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

    @ManyToMany(mappedBy = "")
    @JsonBackReference
    private Set<?> joinedRooms;
}
