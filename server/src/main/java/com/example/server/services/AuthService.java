package com.example.server.services;

import com.example.server.auth.JwtUtil;
import com.example.server.dto.UserDto;
import com.example.server.exceptions.AccountAlreadyExistsException;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.exceptions.UserNotRegisteredException;
import com.example.server.exceptions.WrongPasswordException;
import com.example.server.model.User;
import com.example.server.repositories.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.email())
                .orElseThrow(() -> new UserNotFoundException(""));

        if (user.isActive()) {
            throw new AccountAlreadyExistsException();
        }

        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        userRepository.save(user);

        return jwtUtil.generateConfirmationToken(userDto.email());
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));

        if (!user.isActive()) {
            throw new UserNotRegisteredException();
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongPasswordException();
        }

        return jwtUtil.generateAccessToken(email);
    }

    public String confirmEmail(String token) {
        String email;
        try {
            email = jwtUtil.extractEmailFromConfirmationToken(token);
        } catch (ExpiredJwtException ex) {
            return "Token expired";
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        user.setActive(true);
        userRepository.save(user);
        return "Email successfully confirmed";
    }
}
