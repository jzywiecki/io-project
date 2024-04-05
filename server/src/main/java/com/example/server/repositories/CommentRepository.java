package com.example.server.repositories;

import com.example.server.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * Find all comments by room id.
     * @param roomId the room id.
     * @return the comments.
     */
    List<Comment> findAllByRoomIdAndUserId(Long roomId, Long userId);

    /**
     * Delete all comments by room id and user id.
     * @param userId the user id.
     * @param roomId the room id.
     */
    void deleteAllByUserIdAndRoomId(Long userId, Long roomId);
}
