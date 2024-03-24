package com.example.server.controllers;

import com.example.server.dto.RoomDto;
import com.example.server.dto.TermDto;
import com.example.server.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.server.services.RoomService;
import com.example.server.services.TermService;
import com.example.server.services.UserService;
import lombok.AllArgsConstructor;
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
    @GetMapping("/get-terms-in-room/{roomId}")
    public ResponseEntity<List<TermDto>> getRoomTerms(
            final @PathVariable Long roomId) {
        return ResponseEntity.ok(termService.getRoomTerms(roomId));
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
