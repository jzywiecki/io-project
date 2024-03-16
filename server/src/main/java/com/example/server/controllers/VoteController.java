package com.example.server.controllers;

import com.example.server.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
