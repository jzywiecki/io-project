package com.example.server.controllers;

import com.example.server.dto.RoomDto;
import com.example.server.services.RoomService;
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
     * User service.
     */
    private final UserService userService;

    @GetMapping("/get-user-rooms/{userId}")
    public ResponseEntity<List<RoomDto>> getUserRooms(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserRooms(userId));
    }

}
