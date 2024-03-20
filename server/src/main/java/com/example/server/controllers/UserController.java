package com.example.server.controllers;

import com.example.server.dto.RoomDto;
import com.example.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    /**
     * User service.
     */
    private final UserService userService;
    /**
     * Constructor.
     * @param userServiceInput user service.
     */
    @Autowired
    public UserController(final UserService userServiceInput) {
        this.userService = userServiceInput;
    }

    @GetMapping("/get-user-rooms/{userId}")
    public ResponseEntity<List<RoomDto>> getUserRooms(@PathVariable Long userId) {
        List<RoomDto> userRooms = userService.getUserRooms(userId);
        return new ResponseEntity<>(userRooms, HttpStatus.OK);
    }
}
