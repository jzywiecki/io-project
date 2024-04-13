package com.example.server.services;

import com.example.server.dto.RoomDto;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.Role;
import com.example.server.model.Room;
import com.example.server.model.User;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    /**
     * User repository.
     */
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    /**
     * Assign users to the room.
     * @param emails the user emails list.
     */
    public void setUsersInTheRoom(Long roomId, List<String> emails) {
        Room room = roomRepository.getReferenceById(roomId);
        for (String email : emails) {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                userRepository.save(user.get());
                room.getJoinedUsers().add(user.get());
            } else {
                User newUser = User.builder()
                        .email(email)
                        .active(false)
                        .role(Role.STUDENT)
                        .build();
                userRepository.save(newUser);
                room.getJoinedUsers().add(newUser);
            }
        }
        roomRepository.save(room);
    }

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
