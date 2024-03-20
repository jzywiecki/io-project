package com.example.server.services;

import com.example.server.dto.TermDto;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.TermRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TermService {

    private final TermRepository termRepository;

    private final RoomRepository roomRepository;

    @Autowired
    public TermService(TermRepository termRepository, RoomRepository roomRepository) {
        this.termRepository = termRepository;
        this.roomRepository = roomRepository;
    }

    public List<TermDto> getTermsByRoomId(Long roomId) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(
                        () -> new RoomNotFoundException("Room with id: "
                                + roomId
                                + " not found.")
                );

        List<Term> terms = termRepository.findAllByRoomId(roomId);

        return terms
                .stream()
                .map((term) -> new TermDto(
                        term.getDay(),
                        term.getStartTime(),
                        term.getEndTime()
                ))
                .collect(Collectors.toList());
    }



}
