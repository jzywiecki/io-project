package com.example.server.repositories;

import com.example.server.model.Room;
import com.example.server.model.Term;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class RoomRepositoryTest {
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    TermRepository termRepository;
    
    @Test
    void shouldAssignTermsToRoom() {
        //given
        Room room = Room.builder().build();
        room = roomRepository.save(room);
        Optional<Term> term1 = termRepository.findByDayAndStartTime(DayOfWeek.MONDAY, LocalTime.of(8, 0));
        Optional<Term> term2 = termRepository.findByDayAndStartTime(DayOfWeek.FRIDAY, LocalTime.of(18, 30));
        assertTrue(term1.isPresent());
        assertTrue(term2.isPresent());
        Set<Term> terms = new HashSet<>(List.of(term1.get(), term2.get()));

        //when
        room.setTerms(terms);
        room = roomRepository.save(room);

        //then
        assertEquals(terms, room.getTerms());
    }
}
