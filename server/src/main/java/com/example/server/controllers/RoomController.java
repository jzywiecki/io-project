package com.example.server.controllers;

import com.example.server.services.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/room")
@AllArgsConstructor
public class RoomController {
    /**
     * Room service.
     */
    private final RoomService roomService;

}
