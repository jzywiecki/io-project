package com.example.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST)
    List<Term> terms;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST})
    private Room room;
}
