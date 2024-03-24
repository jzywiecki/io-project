package com.example.server.repositories;

import com.example.server.model.Term;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class TermRepositoryTest {
    @Autowired
    private TermRepository termRepository;

    @Test
    void shouldReturnNumberOfAllTerms() {
        //when
        long numberOfTerms = termRepository.count();

        //then
        assertEquals(35, numberOfTerms);
    }

    @Test
    void shouldReturnEmptyWhenWrongDay() {
        //given
        DayOfWeek day = DayOfWeek.SUNDAY;
        LocalTime startTime = LocalTime.of(8, 0);

        //when
        Optional<Term> term = termRepository.findByDayAndStartTime(day, startTime);

        //then
        assertTrue(term.isEmpty());
    }

    @Test
    void shouldReturnEmptyWhenWrongStartTime() {
        //given
        DayOfWeek day = DayOfWeek.MONDAY;
        LocalTime startTime = LocalTime.of(9, 0);

        //when
        Optional<Term> term = termRepository.findByDayAndStartTime(day, startTime);

        //then
        assertTrue(term.isEmpty());
    }

    @Test
    void shouldReturnProperTermWhenWhenRightDayAndStartTime() {
        //given
        DayOfWeek day = DayOfWeek.MONDAY;
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(9, 30);

        //when
        Optional<Term> term = termRepository.findByDayAndStartTime(day, startTime);

        //then
        assertTrue(term.isPresent());
        assertEquals(endTime, term.get().getEndTime());
    }
}