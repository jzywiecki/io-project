package com.example.server.services;

import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.TermRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final TermRepository termRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, TermRepository termRepository) {
        this.roomRepository = roomRepository;
        this.termRepository = termRepository;
    }

    public void assignTerms(Long roomId, Set<Term> terms) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with id: " + roomId + " not found."));
        termRepository.saveAll(terms);
        room.setTerms(terms);
        roomRepository.save(room);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
}
