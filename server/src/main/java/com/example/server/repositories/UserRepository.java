package com.example.server.repositories;

import com.example.server.model.Room;
import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find rooms where user is member.
     * @param userId the user id.
     * @return the rooms.
     */
    @Query("SELECT DISTINCT r "
            + "FROM User u JOIN u.joinedRooms r WHERE u.id = :userId")
    List<Room> getJoinedRooms(Long userId);

    Optional<User> findByEmail(String email);
}
