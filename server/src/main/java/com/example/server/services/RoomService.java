package com.example.server.services;

import com.example.server.algorithm.Algorithm;
import com.example.server.repositories.ResultRepository;
import com.example.server.model.Term;
import com.example.server.model.Room;
import com.example.server.model.User;
import com.example.server.model.Result;
import com.example.server.model.Vote;
import com.example.server.dto.RoomSummaryDto;
import com.example.server.dto.TermDto;
import com.example.server.dto.TermStringDto;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.exceptions.TermNotFoundException;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.TermRepository;
import com.example.server.repositories.UserRepository;
import com.example.server.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomService {
    /** Room repository. */
    private final RoomRepository roomRepository;
    /** Term repository. */
    private final TermRepository termRepository;
    /** Result repository. */
    private final ResultRepository resultRepository;

    private final MailService mailService;
    /** Runs the algorithm for the given room,
     *  and saves the results in the database.
     * @param roomID room id.
     *  */
    public final void runAlgorithm(final long roomID) {
        List<Term> terms = termRepository.findAllByRoomId(roomID);

        Map<Term, Integer> termToIdx = new HashMap<>();
        Map<Integer, Term> idxToTerm = new HashMap<>();
        int tIdx = 0;
        for (Term term:terms) {
            termToIdx.put(term, tIdx);
            idxToTerm.put(tIdx, term);
            tIdx++;
        }

        Optional<Room> room = roomRepository.findById(roomID);
        if (room.isPresent()) {
            Map<User, List<Term>> map = getUserChoices(room.get());
            Map<Integer, User> idxToUser = new HashMap<>();
            Map<User, Integer> userToIdx = new HashMap<>();
            Map<Integer, int[]> choices = new HashMap<>();

            int uIdx = 0;
            for (Map.Entry<User, List<Term>> entry:map.entrySet()) {
                idxToUser.put(uIdx, entry.getKey());
                userToIdx.put(entry.getKey(), uIdx);

                int n = entry.getValue().size();
                int[] entryChoices = new int[n];

                for (int i = 0; i < n; i++) {
                    entryChoices[i] = termToIdx.get(entry.getValue().get(i));
                }

                choices.put(userToIdx.get(entry.getKey()), entryChoices);

                uIdx++;
            }

            Algorithm algorithm = new Algorithm(terms.size(), choices);
            int[] assignment = algorithm.run();
            System.out.println(Arrays.toString(assignment));
            Map<User, Term> dbAssignment = new HashMap<>();
            for (int i = 0; i < assignment.length; i++) {
                User user = idxToUser.get(i);
                Term term = idxToTerm.get(assignment[i]);
                dbAssignment.put(user, term);
                //System.out.println(user.getId() + " : " + term.getId());
            }

            for (Map.Entry<User, Term> entry : dbAssignment.entrySet()) {
                Result res = Result.builder()
                        .room(room.get())
                        .user(entry.getKey())
                        .term(entry.getValue())
                        .build();
                resultRepository.save(res);
            }

            // send email to all users
            for (Map.Entry<User, Term> entry : dbAssignment.entrySet()) {
                System.out.println("Sending email to "
                        + entry.getKey().getEmail()
                        + " for term "
                        + entry.getValue().getDay()
                        + " "
                        + entry.getValue().getStartTime());
                mailService.send(entry.getKey().getEmail(),
                "[Plan AGH] Twój plan już jest dla przedmiotu "
                        + room.get().getName()
                        + "!", "Otrzymany przez Ciebie termin to "
                        + Utils.getDayInPolish(entry.getValue().getDay())
                        + " "
                        + entry.getValue().getStartTime()
                        + " - "
                        + entry.getValue().getEndTime()
                        + "!");
            }
        }
    }

    private Map<User, List<Term>> getUserChoices(final Room room) {
        List<Vote> votes = room.getVotes();

        Map<User, List<Term>> resultMap = new HashMap<>();
        for (Vote vote : votes) {
            resultMap.computeIfAbsent(vote.getUser(),
                    k -> new ArrayList<>()).add(vote.getTerm());
        }
        return resultMap;
    }

    private final UserRepository userRepository;

    /**
     * Assign terms to the room.
     * @param id the room id.
     * @param termsDto the terms.
     */
    public void assignTerms(final Long id, final List<TermDto> termsDto) {
        Room room = getRoom(id);
        Set<Term> terms = termsDto.stream()
                        .map(term -> termRepository
                                .findByDayAndStartTime(term.day(),
                                                    term.startTime())
                                .orElseThrow(() ->
                                        new TermNotFoundException("Term "
                                                + term.day()
                                                + " "
                                                + term.startTime()
                                                + "not found.")))
                        .collect(Collectors.toSet());
        room.setTerms(terms);
        roomRepository.save(room);
    }
    /**
     * Save the room.
     * @param room the room.
     * @return the room.
     */
    public Room saveRoom(final Room room) {
        return roomRepository.save(room);
    }

    /**
     * Get the room.
     * @param id the room id.
     * @return the room.
     */
    public Room getRoom(final Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() ->
                        new RoomNotFoundException("Room with id: "
                                + id
                                + " not found."));
    }

    /**
     * Get all rooms.
     * @return the rooms.
     */
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    public RoomSummaryDto getRoomInfo(final Long id) {
        Room room = getRoom(id);
        Set<Term> terms = room.getTerms();
        List<TermStringDto> termDtos = terms.stream().map((x)-> new TermStringDto(x.getId(), x.getDay(), x.getStartTime().toString(), x.getEndTime().toString())).toList();
        return new RoomSummaryDto(room.getId(),
                room.getName(),
                room.getDescription(),
                room.getDeadlineDate().toString(),
                room.getDeadlineTime().toString(),
                termDtos);
    }

    public void addUserToRoom(long roomId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found."));

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room with id: " + userId + " not found."));

        room.getJoinedUsers().add(user);
        roomRepository.save(room);
    }

}
