package com.example.server.controllers;

import com.example.server.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
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

}
