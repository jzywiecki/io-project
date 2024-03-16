package com.example.server.controllers;

import com.example.server.services.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
