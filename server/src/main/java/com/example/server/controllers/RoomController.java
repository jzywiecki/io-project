package com.example.server.controllers;

import com.example.server.dto.RoomDto;
import com.example.server.dto.TermDto;
import com.example.server.services.RoomService;
import com.example.server.services.TermService;
import com.example.server.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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
     * @return the rooms.
     */
    @GetMapping("/get-user-rooms/{userId}")
    public ResponseEntity<List<RoomDto>> getUserRooms(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserRooms(userId));
    }

    /**
     * Get all terms in the room
     * @return the terms.
     */
    @GetMapping("/get-terms-in-room/{roomId}")
    public ResponseEntity<List<TermDto>> getRoomTerms(@PathVariable Long roomId) {
        return ResponseEntity.ok(termService.getRoomTerms(roomId));
    }

}
