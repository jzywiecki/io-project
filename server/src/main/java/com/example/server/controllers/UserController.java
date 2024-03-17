package com.example.server.controllers;

import com.example.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    /**
     * User service.
     */
    private final UserService userService;
    /**
     * Constructor.
     * @param userServiceInput user service.
     */
    @Autowired
    public UserController(final UserService userServiceInput) {
        this.userService = userServiceInput;
    }
}
