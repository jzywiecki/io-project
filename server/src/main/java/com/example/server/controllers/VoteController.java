package com.example.server.controllers;

import com.example.server.dto.TermDto;
import com.example.server.dto.VotesDto;
import com.example.server.services.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * Add a vote.
     * @param votesDto the votes dto.
     * @return the response entity.
     */
    @PostMapping("/new-votes")
    public ResponseEntity<?> addVote(final @RequestBody VotesDto votesDto) {
        voteService.vote(votesDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("{\"message\": \"Votes added successfully.\"}");
    }

    @GetMapping("/get-user-votes/{roomId}/{userId}")
    public ResponseEntity<List<TermDto>> getUserVotesInRoom(
            final @PathVariable long roomId,
            final @PathVariable long userId
    ) {


        return ResponseEntity.ok(voteService.getPreviousVotes(userId, roomId));
    }
}
