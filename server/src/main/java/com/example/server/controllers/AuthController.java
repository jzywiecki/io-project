package com.example.server.controllers;

import com.example.server.services.AuthService;
import com.example.server.dto.LoginFormDto;
import com.example.server.dto.UserDto;
import com.example.server.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto userDto) {
        User user = authService.register(userDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginFormDto loginFormDto) {
        String token = authService.login(loginFormDto.email(), loginFormDto.password());
        return ResponseEntity.ok(token);
    }
}
