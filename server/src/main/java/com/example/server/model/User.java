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

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class User {
    /**
     *  User id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     *  User first name.
     */
    private String firstName;

    /**
     *  User last name.
     */
    private String lastName;

    /**
     *  User email in AGH domain.
     */
    private String email;

    /**
     *  User password.
     */
    private String password;

    /**
     *  User role.
     */
    private Role role;

    /**
     *  User active status.
     */
    private boolean active;

    /**
     *  User rooms.
     */
    @ManyToMany(mappedBy = "joinedUsers", fetch = FetchType.LAZY,
            cascade = { CascadeType.PERSIST})
    @JsonBackReference
    private Set<Room> joinedRooms;

    /**
     *  User votes.
     */
    @OneToMany(mappedBy = "user")
    private List<Vote> votes;

    /**
     *  Represents the terms assigned to the user by the algorithm.
     */
    @OneToMany(mappedBy = "user")
    private List<Result> results;
}
