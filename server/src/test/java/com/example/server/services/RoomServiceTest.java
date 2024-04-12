package com.example.server.services;

import com.example.server.dto.TermDto;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.exceptions.TermNotFoundException;
import com.example.server.model.Room;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.TermRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class RoomServiceTest {
    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private TermRepository termRepository;

    @Test
    void shouldThrowExceptionWhenWrongRoomId() {
        long id = 0;
        when(roomRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomService.getRoomInfo(id));
    }

    @Test
    void shouldThrowExceptionWhenWrongTerm() {
        long id = 0;
        TermDto termDto = new TermDto(1, DayOfWeek.MONDAY, LocalTime.of(0, 0), LocalTime.of(0, 0));
        when(roomRepository.findById(id)).thenReturn(Optional.of(Room.builder().build()));
        when(termRepository.findByDayAndStartTime(termDto.day(), termDto.startTime())).thenReturn(Optional.empty());

        assertThrows(TermNotFoundException.class, () -> roomService.assignTerms(id, List.of(termDto)));
    }
}
