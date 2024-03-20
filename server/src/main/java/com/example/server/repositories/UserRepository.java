package com.example.server.repositories;

import com.example.server.model.Room;
import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.joinedRooms FROM User u WHERE u.id = :userId")
    List<Room> getJoinedRooms(Long userId);
}
