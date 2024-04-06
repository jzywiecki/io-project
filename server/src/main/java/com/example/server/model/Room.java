package com.example.server.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
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
     *  Room finished status.
     */    
    private Boolean finished;
    /**
     *  Room description.
     */
    private String description;
    /**
     *  Room deadline date.
     */
    private Date deadlineDate;
    /**
     *  Room deadline time.
     */
    private LocalTime deadlineTime;
    /**
     *  Room owner.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST})
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
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST})
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
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER,
            cascade = { CascadeType.PERSIST})
    private List<Vote> votes;

    /**
     *  Represents the results of the algorithm for the room after stop voting.
     */
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER,
            cascade = { CascadeType.PERSIST})
    private List<Result> results;

    /**
     *  Terms setter.
     * @param termSet Set of terms to be set.
     */
    public void setTerms(final Set<Term> termSet) {
        this.terms = termSet;
    }
}
