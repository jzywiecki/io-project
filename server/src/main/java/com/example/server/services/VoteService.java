package com.example.server.services;

import com.example.server.dto.TermDto;
import com.example.server.dto.VotesDto;
import com.example.server.dto.VotingPageDto;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.exceptions.TermNotFoundException;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.*;
import com.example.server.repositories.*;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final CommentRepository commentRepository;





    /**
     * Get voting page.
     * @param roomId the room id.
     * @param userId the user id.
     * @return the voting page dto.
     */
    public VotingPageDto getVotingPage(long roomId, long userId) {
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

        List<Term> availableTerms = termRepository
                .findAllByRoomId(roomId);

        List<Comment> comments = commentRepository
                .findAllByRoomIdAndUserId(roomId, userId);

        List<TermDto> availableTermsDto = availableTerms.stream()
                .map(term -> TermDto
                        .builder()
                        .id(term.getId())
                        .day(term.getDay())
                        .startTime(term.getStartTime())
                        .endTime(term.getEndTime())
                        .build())
                .toList();

        Map<Long, Boolean> termWithIdIsSelected = availableTerms.stream()
                .collect(Collectors.toMap(
                        Term::getId,
                        term -> previousVotes.stream()
                                .map(Vote::getTerm)
                                .map(Term::getId)
                                .anyMatch(id -> id.equals(term.getId()))
                ));

        Map<Long, String> termWithIdComments = availableTerms.stream()
                .collect(Collectors.toMap(
                        term -> term.getId(),
                        term -> {
                            Comment comment = comments.stream()
                                    .filter(c -> c.getTerm() != null && c.getTerm().getId().equals(term.getId()))
                                    .findFirst()
                                    .orElse(null);
                            return comment != null ? comment.getContent() : "";
                        }
                ));

        return new VotingPageDto(
                availableTermsDto,
                termWithIdIsSelected,
                termWithIdComments);
    }
}
