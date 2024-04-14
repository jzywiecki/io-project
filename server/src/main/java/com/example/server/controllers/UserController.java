package com.example.server.controllers;

import com.example.server.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    /**
     * User service.
     */
    private final UserService userService;


    /**
     * Assign users to the room.
     * @param id the room id.
     * @param emails the list of user emails
     * @return the http status.
     */
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/{id}/users")
    public ResponseEntity<HttpStatus> assignUsers(
            final @PathVariable Long id,
            final @RequestBody List<String> emails) {
        userService.setUsersInTheRoom(id, emails);
        return new ResponseEntity<>(OK);
    }
}
