package com.example.server.services;

import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.repositories.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void assignTerms(Long roomId, Set<Term> terms) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with id: " + roomId + " not found."));
        room.setTerms(terms);
        roomRepository.save(room);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
}
