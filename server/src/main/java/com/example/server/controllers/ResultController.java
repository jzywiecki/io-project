package com.example.server.controllers;

import com.example.server.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
