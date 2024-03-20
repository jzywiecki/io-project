package com.example.server.services;

import com.example.server.dto.TermDto;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.exceptions.TermNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final TermRepository termRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, TermRepository termRepository) {
        this.roomRepository = roomRepository;
        this.termRepository = termRepository;
    }

    public void assignTerms(Long id, List<TermDto> termsDto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room with id: " + id + " not found."));

        Set<Term> terms = termsDto.stream()
                        .map(term -> termRepository.findByDayAndStartTime(term.day(), term.startTime())
                                .orElseThrow(() -> new TermNotFoundException("Term " + term.day() + " " + term.startTime() + "not found.")))
                        .collect(Collectors.toSet());
        room.setTerms(terms);
        roomRepository.save(room);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoom(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException("Room with id: " + id + " not found."));
    }

    public List<Room> getRooms() {
        return roomRepository.findAll();
    }
}
