package com.example.server.repositories;

import com.example.server.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    /**
     * Find all results by room id.
     * @param roomId the room id.
     * @return list of results in room.
     */
    List<Result> findAllByRoomId(long roomId);

    /**
     * Find result by room id and user id.
     * @param roomId the room id.
     * @param userId the user id.
     * @return result of user in room.
     */
    Optional<Result> findByRoomIdAndUserId(long roomId, long userId);
}
