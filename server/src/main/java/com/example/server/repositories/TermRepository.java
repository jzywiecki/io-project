package com.example.server.repositories;

import com.example.server.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
    /**
     * Find all terms by room id.
     * @param roomId the room id.
     * @return the terms.
     */
    @Query(
            "SELECT term "
                    + "FROM Term term "
                    + "INNER JOIN term.rooms room "
                    + "WHERE room.id = :roomId"
    )
    List<Term> findAllByRoomId(Long roomId);
}
