package com.example.server.controllers;

import com.example.server.dto.VoteDto;
import com.example.server.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/vote")
public class VoteController {
    /**
     * Vote service.
     */
    private final VoteService voteService;
    /**
     * Constructor.
     * @param voteServiceInput vote service.
     */
    @Autowired
    public VoteController(final VoteService voteServiceInput) {
        this.voteService = voteServiceInput;
    }

    @PostMapping("/add-vote")
    public ResponseEntity<?> addVote(@RequestBody VoteDto voteDto) {
        voteService.addNewVote(voteDto);
        return ResponseEntity.ok("Vote added successfully");
    }
}
