package com.example.server.services;

import com.example.server.dto.VotesDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
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
     * Constructor.
     * @param userRepositoryInput user repository.
     * @param termRepositoryInput term repository.
     * @param roomRepositoryInput room repository.
     * @param voteRepositoryInput vote repository.
     */
    @Autowired
    public VoteService(final UserRepository userRepositoryInput,
                       final TermRepository termRepositoryInput,
                       final RoomRepository roomRepositoryInput,
                       final VoteRepository voteRepositoryInput) {
        this.userRepository = userRepositoryInput;
        this.termRepository = termRepositoryInput;
        this.roomRepository = roomRepositoryInput;
        this.voteRepository = voteRepositoryInput;
    }
    /**
     * Add a new vote.
     */
    @Transactional
    public void vote(final VotesDto votesDto) {

        List<Vote> votesEntityToSave = new ArrayList<>();

        User user = userRepository
                .findById(votesDto.user_id())
                .orElseThrow(
                        () -> new UserNotFoundException("User with id: "
                                + votesDto.user_id()
                                + " not found.")
                );

        Room room = roomRepository
                .findById(votesDto.room_id())
                .orElseThrow(
                        () -> new RoomNotFoundException("Room with id: "
                                + votesDto.room_id()
                                + " not found.")
                );

        voteRepository.deleteAllByUserIdAndRoomId(user.getId(), room.getId());

        for (Long termId : votesDto.terms_id()) {

            Term term = termRepository
                    .findById(termId)
                    .orElseThrow(
                            () -> new TermNotFoundException("Term with id: "
                                    + termId
                                    + " not found.")
                    );

            Vote vote = Vote.builder()
                    .user(user)
                    .term(term)
                    .room(room)
                    .build();

            votesEntityToSave.add(vote);
        }

        voteRepository.saveAll(votesEntityToSave);
    }

}
