package com.example.server.services;

import com.example.server.dto.RoomDto;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.User;
import com.example.server.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    /**
     * User repository.
     */
    private final UserRepository userRepository;

    /**
     * Get all rooms where user is a member.
     * @param userId the user id.
     * @return the rooms of a user.
     */
    public List<RoomDto> getUserRooms(final Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException("User with id: "
                                + userId
                                + " not found.")
                );
        List<Room> userRooms = userRepository.getJoinedRooms(userId);

        return userRooms.stream()
                .map((room) -> new RoomDto(
                        room.getId(),
                        room.getName(),
                        room.getDescription(),
                        room.getDeadlineDate(),
                        room.getDeadlineTime()
                ))
                .collect(Collectors.toList());
    }
}
