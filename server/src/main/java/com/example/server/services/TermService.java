package com.example.server.services;

import com.example.server.dto.TermDto;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.TermRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TermService {

    /**
     * Room repository.
     */
    private final TermRepository termRepository;

    /**
     * Room repository.
     */
    private final RoomRepository roomRepository;

    /**
     * Get all available terms in the room.
     * @param roomId the room id.
     * @return the terms.
     */
    public List<TermDto> getRoomTerms(final Long roomId) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(
                        () -> new RoomNotFoundException("Room with id: "
                                + roomId
                                + " not found.")
                );

        List<Term> terms = termRepository.findAllByRoomId(roomId);

        return terms.stream()
                .map((term) -> new TermDto(
                        term.getId(),
                        term.getDay(),
                        term.getStartTime(),
                        term.getEndTime()
                ))
                .collect(Collectors.toList());
    }
}
