package com.example.server.services;

import com.example.server.dto.VoteDto;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.exceptions.TermNotFoundException;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.exceptions.VoteNotFoundException;
import com.example.server.model.Room;
import com.example.server.model.Term;
import com.example.server.model.User;
import com.example.server.model.Vote;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.TermRepository;
import com.example.server.repositories.UserRepository;
import com.example.server.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VoteService {

    private final UserRepository userRepository;

    private final TermRepository termRepository;

    private final RoomRepository roomRepository;

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(UserRepository userRepository, TermRepository termRepository, RoomRepository roomRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.termRepository = termRepository;
        this.roomRepository = roomRepository;
        this.voteRepository = voteRepository;
    }

    public void addNewVote(VoteDto voteDto) {
        User user = userRepository
                .findById(voteDto.user_id())
                .orElseThrow(
                        () -> new UserNotFoundException("User with id: " + voteDto.user_id() + " not found.")
                );

        Term term = termRepository
                .findById(voteDto.term_id())
                .orElseThrow(
                        () -> new TermNotFoundException("Term with id: " + voteDto.term_id() + " not found.")
                );

        Room room = roomRepository
                .findById(voteDto.room_id())
                .orElseThrow(
                        () -> new RoomNotFoundException("Room with id: " + voteDto.room_id() + " not found.")
                );

        Vote vote = Vote.builder()
                .user(user)
                .term(term)
                .room(room)
                .build();

        voteRepository.save(vote);
    }
}
