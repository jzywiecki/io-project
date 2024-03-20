package com.example.server.repositories;

import com.example.server.model.Room;
import com.example.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public List<Room> getUserRooms(Long userId);
}
