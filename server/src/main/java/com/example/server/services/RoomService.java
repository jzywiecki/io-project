package com.example.server.services;

import com.example.server.dto.RoomSummaryDto;
import com.example.server.dto.TermDto;
import com.example.server.dto.TermStringDto;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.exceptions.TermNotFoundException;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.model.User;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.TermRepository;
import com.example.server.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {
    /**
     * Room repository.
     */
    private final RoomRepository roomRepository;
    /**
     * Term repository.
     */
    private final TermRepository termRepository;

    private final UserRepository userRepository;

    /**
     * Assign terms to the room.
     * @param id the room id.
     * @param termsDto the terms.
     */
    public void assignTerms(final Long id, final List<TermDto> termsDto) {
        Room room = getRoom(id);
        Set<Term> terms = termsDto.stream()
                        .map(term -> termRepository
                                .findByDayAndStartTime(term.day(),
                                                    term.startTime())
                                .orElseThrow(() ->
                                        new TermNotFoundException("Term "
                                                + term.day()
                                                + " "
                                                + term.startTime()
                                                + "not found.")))
                        .collect(Collectors.toSet());
        room.setTerms(terms);
        roomRepository.save(room);
    }
    /**
     * Save the room.
     * @param room the room.
     * @return the room.
     */
    public Room saveRoom(final Room room) {
        return roomRepository.save(room);
    }

    /**
     * Get the room.
     * @param id the room id.
     * @return the room.
     */
    public Room getRoom(final Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() ->
                        new RoomNotFoundException("Room with id: "
                                + id
                                + " not found."));
    }

    /**
     * Get all rooms.
     * @return the rooms.
     */
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public RoomSummaryDto getRoomInfo(final Long id) {
        Room room = getRoom(id);
        Set<Term> terms = room.getTerms();
        List<TermStringDto> termDtos = terms.stream().map((x)-> new TermStringDto(x.getId(), x.getDay(), x.getStartTime().toString(), x.getEndTime().toString())).toList();
        return new RoomSummaryDto(room.getId(),
                room.getName(),
                room.getDescription(),
                room.getDeadlineDate().toString(),
                room.getDeadlineTime().toString(),
                termDtos);
    }

    public void addUserToRoom(long roomId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found."));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with id: " + userId + " not found."));

        room.getJoinedUsers().add(user);
        roomRepository.save(room);
    }
}
