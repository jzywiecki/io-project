package com.example.server.services;

import com.example.server.auth.JwtUtil;
import com.example.server.dto.UserDto;
import com.example.server.exceptions.*;
import com.example.server.model.Room;
import com.example.server.model.User;
import com.example.server.repositories.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MailService mailService;

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

        String token = jwtUtil.generateConfirmationToken(userDto.email());

        String msg = "Potwierdź swój email, klikając w link:\n"
                + "http://54.166.152.243/confirmEmail/"
                + token;

        mailService.send(userDto.email(), "Potwierdź swój email", msg);

        return token;
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

    public void confirmEmail(String token) {
        String email = jwtUtil.extractEmailFromConfirmationToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        user.setActive(true);
        userRepository.save(user);
    }

    public void checkUserPermissionsForRoom(long roomId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));

        for (Room room : user.getJoinedRooms()) {
            if (room.getId() == roomId) {
                return;
            }
        }

        throw new NoUserPermissionsForRoom();
    }

    public long getUserIdFromContext() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));
        return user.getId();
    }
}
