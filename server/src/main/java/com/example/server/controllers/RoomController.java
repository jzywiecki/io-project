package com.example.server.controllers;

import com.example.server.dto.RoomDto;
import com.example.server.dto.TermDto;
import com.example.server.services.RoomService;
import com.example.server.services.TermService;
import com.example.server.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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

    /**
     * Runs the algorithm for a given room.
     * @param roomId room id
     */
    @GetMapping("/stop-voting/{roomId}")
    public final void stopVoting(
            final @PathVariable Long roomId) {
        roomService.runAlgorithm(roomId);
    }

}
