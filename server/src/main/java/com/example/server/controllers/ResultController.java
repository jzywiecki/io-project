package com.example.server.controllers;

import com.example.server.dto.ResultsDto;
import com.example.server.dto.UserResultsDto;
import com.example.server.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin
@RequestMapping("/api/result")
public class ResultController {
    /**
     * Result service.
     */
    private final ResultService resultService;
    /**
     * Constructor.
     * @param resultServiceInput room service.
     */
    @Autowired
    public ResultController(final ResultService resultServiceInput) {
        this.resultService = resultServiceInput;
    }

    /**
     * Get the results of all users in a room.
     * @param roomId the room id.
     * @return the results of all users in a room.
     */
    @GetMapping("/get-results/{roomId}")
    public ResponseEntity<ResultsDto> getResults(
            final @PathVariable int roomId) {
        return ResponseEntity.ok(resultService.getResults(roomId));
    }

    /**
     * Get the results of a user in a room.
     * @param roomId the room id.
     * @param userId the user id.
     * @return the results of a user in a room.
     */
    @GetMapping("/get-results/{roomId}/{userId}")
    public ResponseEntity<UserResultsDto> getResultsByUser(
            final @PathVariable int roomId,
            final @PathVariable int userId) {
        return ResponseEntity.ok(resultService.getUserResults(roomId, userId));
    }

}
