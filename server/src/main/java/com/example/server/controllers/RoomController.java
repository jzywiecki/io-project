package com.example.server.controllers;

import com.example.server.dto.RoomDto;
import com.example.server.dto.RoomSummaryDto;
import com.example.server.dto.TermDto;
import com.example.server.model.Room;
import com.example.server.services.RoomService;
import com.example.server.services.TermService;
import com.example.server.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/room")
@AllArgsConstructor
public class RoomController {
    /**
     * Room service.
     */
    private final RoomService roomService;

    /**
     * Term service.
     */
    private final TermService termService;

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Get all rooms to which the user is assigned.
     * @param userId the user id.
     * @return the rooms.
     */
    @GetMapping("/get-user-rooms/{userId}")
    public ResponseEntity<List<RoomDto>> getUserRooms(
            final @PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserRooms(userId));
    }

    /**
     * Get all terms in the room.
     * @param roomId the room id.
     * @return the terms.
     */
    @GetMapping("/get-terms-in-room/{roomId}/{userId}")
    public ResponseEntity<List<TermDto>> getRoomTerms(
            final @PathVariable Long roomId,
            final @PathVariable Long userId) {
        roomService.addUserToRoom(roomId, userId);
        List<TermDto> terms = termService.getRoomTerms(roomId);
        return ResponseEntity.ok(terms);
    }

    /**

     * Runs the algorithm for a given room.
     * @param roomId room id
     */
    @PostMapping("/stop-voting/{roomId}")
    public final void stopVoting(
            final @PathVariable Long roomId) {
        roomService.runAlgorithm(roomId);
    }


    /**
     * Create a room.
     * @param roomDto the room dto.
     * @return the room dto.
     */
    @PostMapping("")
    public ResponseEntity<RoomDto> createRoom(
            final @RequestBody RoomDto roomDto) {
        Room room = Room.builder()
                .name(roomDto.name())
                .description(roomDto.description())
                .deadlineDate(roomDto.deadlineDate())
                .deadlineTime(roomDto.deadlineTime())
                .finished(false)
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

    /**
     * Assign terms to the room.
     * @param id the room id.
     * @param termsDto the terms dto.
     * @return the http status.
     */
    @PutMapping("/{id}/terms")
    public ResponseEntity<HttpStatus> assignTerms(
            final@PathVariable Long id,
            final @RequestBody List<TermDto> termsDto) {
        roomService.assignTerms(id, termsDto);
        return new ResponseEntity<>(OK);
    }

    /**
     * Get the room by id.
     * @param id the room id.
     * @return the room.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoomSummaryDto> getRoom(final @PathVariable Long id) {
        RoomSummaryDto roomSummaryDto = roomService.getRoomInfo(id);
        return new ResponseEntity<>(roomSummaryDto, OK);
    }

    /**
     * Get all rooms.
     * @return the rooms.
     */
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
