package com.example.server.repositories;

import com.example.server.model.Term;
import com.example.server.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
    /**
     * Find term by day and start time.
     * @param day
     * @param startTime
     * @return the term.
     */
     Optional<Term> findByDayAndStartTime(DayOfWeek day, LocalTime startTime);

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
