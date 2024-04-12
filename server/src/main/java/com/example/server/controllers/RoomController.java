package com.example.server.controllers;

import com.example.server.dto.RoomDto;
import com.example.server.dto.RoomSummaryDto;
import com.example.server.dto.TermDto;
import com.example.server.model.Room;
import com.example.server.services.AuthService;
import com.example.server.services.RoomService;
import com.example.server.services.TermService;
import com.example.server.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
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

    private final AuthService authService;

    /**
     * Get all rooms to which the user is assigned.
     * @return the rooms.
     */
    @GetMapping("/get-user-rooms")
    public ResponseEntity<List<RoomDto>> getUserRooms() {
        long userId = authService.getUserIdFromContext();
        return ResponseEntity.ok(userService.getUserRooms(userId));
    }

    /**
     * Get all terms in the room.
     * @param roomId the room id.
     * @return the terms.
     */
    @GetMapping("/get-terms-in-room/{roomId}")
    public ResponseEntity<List<TermDto>> getRoomTerms(
            final @PathVariable Long roomId) {
        authService.checkUserPermissionsForRoom(roomId);
        List<TermDto> terms = termService.getRoomTerms(roomId);
        return ResponseEntity.ok(terms);
    }

    /**

     * Runs the algorithm for a given room.
     * @param roomId room id
     */
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/stop-voting/{roomId}")
    public void stopVoting(
            final @PathVariable Long roomId) {
        roomService.runAlgorithm(roomId);
    }


    /**
     * Create a room.
     * @param roomDto the room dto.
     * @return the room dto.
     */
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("")
    public ResponseEntity<RoomDto> createRoom(
            final @RequestBody RoomDto roomDto) {
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

    /**
     * Assign terms to the room.
     * @param id the room id.
     * @param termsDto the terms dto.
     * @return the http status.
     */
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/{id}/terms")
    public ResponseEntity<HttpStatus> assignTerms(
            final @PathVariable Long id,
            final @RequestBody List<TermDto> termsDto) {
        roomService.assignTerms(id, termsDto);
        return new ResponseEntity<>(OK);
    }

    /**
     * Get the room by id.
     * @param id the room id.
     * @return the room.
     */
    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/{id}")
    public ResponseEntity<RoomSummaryDto> getRoom(final @PathVariable Long id) {
        RoomSummaryDto roomSummaryDto = roomService.getRoomInfo(id);
        return new ResponseEntity<>(roomSummaryDto, OK);
    }

    /**
     * Get all rooms.
     * @return the rooms.
     */
    @PreAuthorize("hasRole('TEACHER')")
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
