package com.example.server.dto;

import com.example.server.model.Role;

public record LoginResponse(String token, Role role) {
}
