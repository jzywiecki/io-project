package com.example.server.services;

import com.example.server.dto.RoomDto;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.User;
import com.example.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<RoomDto> getUserRooms(Long userId) {

        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException("User with id: "
                                + userId
                                + " not found.")
                );
        List<Room> userRooms = userRepository.getJoinedRooms(userId);

        return userRooms.stream()
                .map((room) -> new RoomDto (
                        room.getName(),
                        room.getDescription(),
                        room.getDeadlineDate(),
                        room.getDeadlineTime()
                ))
                .collect(Collectors.toList());
    }
}
