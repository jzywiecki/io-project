package com.example.server.controllers;

import com.example.server.dto.VotingPageDto;
import com.example.server.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/vote")
@AllArgsConstructor
public class VoteController {
    /**
     * Vote service.
     */
    private final VoteService voteService;

    /**
     * Adding new user votes and deleting previous ones.
     * @param roomId, userId.
     */
    @GetMapping("/get-voting-page/{roomId}/{userId}")
    public ResponseEntity<VotingPageDto> getVotingPage(
            final @PathVariable long roomId,
            final @PathVariable long userId
    ) {
        return ResponseEntity.ok(voteService.getVotingPage(roomId, userId));
    }

}
