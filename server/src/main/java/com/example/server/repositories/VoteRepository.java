package com.example.server.repositories;

import com.example.server.model.Room;
import com.example.server.model.User;
import com.example.server.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    /**
     * Delete all votes by user id and room id.
     * @param userId the user id.
     * @param roomId the room id.
     */
    void deleteAllByUserIdAndRoomId(Long userId, Long roomId);

    /**
     * Get user votes selected in previous voting.
     * @param user
     * @param room
     * @return user votes in room.
     */
    List<Vote> findAllByUserAndRoom(User user, Room room);
}
