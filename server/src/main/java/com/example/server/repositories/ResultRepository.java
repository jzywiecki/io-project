package com.example.server.repositories;

import com.example.server.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findAllByRoomId(long roomId);

    Optional<Result> findByRoomIdAndUserId(long roomId, long userId);
}
