package com.example.server.controllers;

import com.example.server.dto.RoomDto;
import com.example.server.dto.TermDto;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api/room")
public class RoomController {
    /**
     * Room service.
     */
    private final RoomService roomService;
    /**
     * Constructor.
     * @param roomServiceInput room service.
     */
    @Autowired
    public RoomController(final RoomService roomServiceInput) {
        this.roomService = roomServiceInput;
    }

    @PostMapping("/")
    public ResponseEntity<Room> createRoom(@RequestBody RoomDto roomDto) {
        Room room = Room.builder()
                .name(roomDto.name())
                .description(roomDto.description())
                .deadlineDate(roomDto.deadlineDate())
                .deadlineTime(roomDto.deadlineTime())
                .build();
        Room savedRoom = roomService.saveRoom(room);
        return new ResponseEntity<>(savedRoom, OK);
    }

    @PutMapping("/{id}/terms")
    public ResponseEntity<HttpStatus> assignTerms(@PathVariable Long id, @RequestBody List<TermDto> termsDto) {
        Set<Term> terms = termsDto.stream()
                .map(termDto -> Term.builder()
                        .day(termDto.day())
                        .startTime(termDto.startTime())
                        .endTime(termDto.endTime())
                        .build()
                ).collect(Collectors.toSet());

        try {
            roomService.assignTerms(id, terms);
        } catch (RoomNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(OK);
    }
}
