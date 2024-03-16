package com.example.server.controllers;

import com.example.server.dto.ResultsDto;
import com.example.server.dto.UserResultsDto;
import com.example.server.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-results/{roomId}")
    public ResponseEntity<ResultsDto> getResults(@PathVariable int roomId) {
        return ResponseEntity.ok(resultService.getResults(roomId));
    }

    @GetMapping("/get-results/{roomId}/{userId}")
    public ResponseEntity<UserResultsDto> getResultsByUser(@PathVariable int roomId, @PathVariable int userId) {
        return ResponseEntity.ok(resultService.getUserResults(roomId, userId));
    }

}
