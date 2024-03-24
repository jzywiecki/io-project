package com.example.server.repositories;

import com.example.server.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {
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