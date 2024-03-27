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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    // IMPORTANT!
    // vote method should be transactional
    // and should delete all votes for the
    // user and room before saving new votes.
    // Now we can add vote but not remove previous.
    // FIXME: During call saveAll(List<Vote>),
    // FIXME: database reports error SQLITE_BUSY.
    //
    /**
     * Adding new user votes and deleting previous ones.
     * @param votesDto
     */
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void vote(final VotesDto votesDto) {

        List<Vote> votesEntityToSave = new ArrayList<>();

        User user = userRepository
                .findById(votesDto.userId())
                .orElseThrow(
                        () -> new UserNotFoundException("User with id: "
                                + votesDto.userId()
                                + " not found.")
                );

        Room room = roomRepository
                .findById(votesDto.roomId())
                .orElseThrow(
                        () -> new RoomNotFoundException("Room with id: "
                                + votesDto.roomId()
                                + " not found.")
                );

        //voteRepository.deleteAllByUserIdAndRoomId(user.getId(), room.getId());
        for (Long termId : votesDto.termsId()) {
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
