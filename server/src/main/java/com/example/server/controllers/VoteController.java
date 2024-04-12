package com.example.server.controllers;

import com.example.server.dto.UserPreferences;
import com.example.server.dto.VotingPageDto;
import com.example.server.services.AuthService;
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
    private final AuthService authService;

    /**
     * Adding new user votes and deleting previous ones.
     * @param roomId
     */
    @GetMapping("/voting-page/{roomId}")
    public ResponseEntity<VotingPageDto> getVotingPage(
            final @PathVariable long roomId) {
        long userId = authService.getUserIdFromContext();
        authService.checkUserPermissionsForRoom(roomId);
        return ResponseEntity.ok(voteService.getVotingPage(roomId, userId));
    }

    /**
     * Save user preferences.
     * @param roomId, votingPageDto.
     */
    @PostMapping(value="/save-preferences/{roomId}", consumes="application/json")
    public ResponseEntity<Void> savePreferences(
            final @PathVariable long roomId,
            final @RequestBody UserPreferences votingPageDto
    ) {
        long userId = authService.getUserIdFromContext();
        authService.checkUserPermissionsForRoom(roomId);
        voteService.savePreferences(roomId, userId, votingPageDto);
        return ResponseEntity.ok().build();
    }

}
