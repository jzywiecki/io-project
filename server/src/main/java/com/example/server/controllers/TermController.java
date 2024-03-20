package com.example.server.controllers;

import com.example.server.dto.RoomDto;
import com.example.server.dto.TermDto;
import com.example.server.services.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/term")
public class TermController {
    /**
     * Term service.
     */
    private final TermService termService;
    /**
     * Constructor.
     * @param termServiceInput term service.
     */
    @Autowired
    public TermController(final TermService termServiceInput) {
        this.termService = termServiceInput;
    }

    @GetMapping("/get-terms-by-roomid/{roomId}")
    public ResponseEntity<List<TermDto>> getTermsByRoomId(@PathVariable Long roomId) {
        List<TermDto> rooms = termService.getTermsByRoomId(roomId);
        return ResponseEntity.ok(rooms);
    }
}
