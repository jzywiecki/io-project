package com.example.server.repositories;

import com.example.server.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    /**
     * Delete all votes by user id and room id.
     * @param userId the user id.
     * @param roomId the room id.
     */
    void deleteAllByUserIdAndRoomId(Long userId, Long roomId);
}
