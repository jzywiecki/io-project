package com.example.server.services;

import com.example.server.dto.VoteDto;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.exceptions.TermNotFoundException;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.model.User;
import com.example.server.model.Vote;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.TermRepository;
import com.example.server.repositories.UserRepository;
import com.example.server.repositories.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteService {
    /**
     * User repository.
     */
    private final UserRepository userRepository;
    /**
     * Term repository.
     */
    private final TermRepository termRepository;
    /**
     * Room repository.
     */
    private final RoomRepository roomRepository;
    /**
     * Vote repository.
     */
    private final VoteRepository voteRepository;

    /**
     * Add a new vote.
     * @param voteDto the vote dto.
     */
    public void addNewVote(final VoteDto voteDto) {
        User user = userRepository
                .findById(voteDto.user_id())
                .orElseThrow(
                        () -> new UserNotFoundException("User with id: "
                                                        + voteDto.user_id()
                                                        + " not found.")
                );

        Term term = termRepository
                .findById(voteDto.term_id())
                .orElseThrow(
                        () -> new TermNotFoundException("Term with id: "
                                                        + voteDto.term_id()
                                                        + " not found.")
                );

        Room room = roomRepository
                .findById(voteDto.room_id())
                .orElseThrow(
                        () -> new RoomNotFoundException("Room with id: "
                                                        + voteDto.room_id()
                                                        + " not found.")
                );

        Vote vote = Vote.builder()
                .user(user)
                .term(term)
                .room(room)
                .build();

        voteRepository.save(vote);
    }
}
