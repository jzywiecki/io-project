package com.example.server.services;

import com.example.server.dto.TermDto;
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
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    /**
     * Adding new user votes and deleting previous ones.
     * @param votesDto
     */
    @Transactional
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

        voteRepository.deleteAllByUserIdAndRoomId(user.getId(), room.getId());
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
    /**
     * Get previous votes of a user in a room.
     * @param userId the user id.
     * @param roomId the room id.
     * @return the list of terms.
     */
    public List<TermDto> getPreviousVotes(final long userId,
                                          final long roomId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException("User with id: "
                                + userId
                                + " not found.")
                );

        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(
                        () -> new RoomNotFoundException("Room with id: "
                                + roomId
                                + " not found.")
                );

        List<Vote> previousVotes = voteRepository
                .findAllByUserAndRoom(user, room);
        List<Term> selectedTerms = previousVotes.stream()
                .map(Vote::getTerm)
                .map(term -> (Term) Hibernate.unproxy(term))
                .toList();

        return selectedTerms.stream()
                .map(term -> TermDto
                        .builder()
                        .id(term.getId())
                        .day(term.getDay())
                        .startTime(term.getStartTime())
                        .endTime(term.getEndTime())
                        .build())
                .toList();
    }
}
