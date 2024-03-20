package com.example.server.controllers;

import com.example.server.dto.RoomDto;
import com.example.server.dto.TermDto;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping("")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto roomDto) {
        Room room = Room.builder()
                .name(roomDto.name())
                .description(roomDto.description())
                .deadlineDate(roomDto.deadlineDate())
                .deadlineTime(roomDto.deadlineTime())
                .build();
        Room savedRoom = roomService.saveRoom(room);
        RoomDto savedRoomDto = RoomDto.builder()
                .id(savedRoom.getId())
                .name(savedRoom.getName())
                .description(savedRoom.getDescription())
                .deadlineDate(savedRoom.getDeadlineDate())
                .deadlineTime(savedRoom.getDeadlineTime())
                .build();
        return new ResponseEntity<>(savedRoomDto, OK);
    }

    @PutMapping("/{id}/terms")
    public ResponseEntity<HttpStatus> assignTerms(@PathVariable Long id, @RequestBody List<TermDto> termsDto) {
        roomService.assignTerms(id, termsDto);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        Room room = roomService.getRoom(id);
        return new ResponseEntity<>(room, OK);
    }

    @GetMapping("")
    public ResponseEntity<List<RoomDto>> getRooms() {
        List<RoomDto> rooms =  roomService.getRooms().stream()
                .map(room -> RoomDto.builder()
                        .id(room.getId())
                        .name(room.getName())
                        .description(room.getDescription())
                        .deadlineDate(room.getDeadlineDate())
                        .deadlineTime(room.getDeadlineTime())
                        .build())
                .toList();
        return new ResponseEntity<>(rooms, OK);
    }
}
