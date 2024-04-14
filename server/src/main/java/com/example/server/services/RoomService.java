package com.example.server.services;

import com.example.server.algorithm.Algorithm;
import com.example.server.dto.*;
import com.example.server.model.*;
import com.example.server.repositories.*;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.exceptions.TermNotFoundException;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
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
    /** Mail service. */
    private final MailService mailService;

    /**
     * Runs algorithm, before marks room as finished (it means that the algorithm has been already calculated)
     * If room is empty we just return, because the algorithm fails and throws an error if there is no votes
     *
     * @param roomID id of the room
     */
    public final void runAlgorithm(final long roomID, final int maxTerms) {
        Optional<Room> roomOptional = roomRepository.findById(roomID);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            if (room.getFinished()) {
                resultRepository.deleteAllByRoomId(roomID);
            }
            room.setFinished(true);
            roomRepository.save(room);


            if (room.getVotes().isEmpty()) {
                System.out.println("No votes in room " + room.getId() + ". Skipping algorithm.");
                return;
            }

            List<Term> terms = termRepository.findAllByRoomId(roomID);
            Map<User, List<Term>> userChoices = getUserChoices(room);
            int termCount = terms.size();

            Map<Integer, User> idxToUser = new HashMap<>();
            Map<Integer, int[]> choices = new HashMap<>();

            int idx = 0;
            for (Map.Entry<User, List<Term>> entry : userChoices.entrySet()) {
                User user = entry.getKey();
                List<Term> userTerms = entry.getValue();
                idxToUser.put(idx, user);

                int[] userChoicesIdx = userTerms.stream()
                        .mapToInt(terms::indexOf)
                        .toArray();
                choices.put(idx, userChoicesIdx);

                idx++;
            }

            System.out.println("Running algorithm for room " + room.getId() + " with " + userChoices.size() + " users and " + termCount + " terms.");
            Algorithm algorithm = new Algorithm(termCount, choices);
            int[] assignment = algorithm.run(
                    maxTerms != -1 && maxTerms < 1 ? -1 : maxTerms
            );
            Map<User, Term> userTermMap = new HashMap<>();
            for (int i = 0; i < assignment.length; i++) {
                User user = idxToUser.get(i);
                Term term = terms.get(assignment[i]);
                userTermMap.put(user, term);
            }

            userTermMap.forEach((user, term) -> {
                Result result = Result.builder()
                        .room(room)
                        .user(user)
                        .term(term)
                        .build();
                resultRepository.save(result);
            });

        }
    }

    /**
     * Send an email with results to every person in a given room.
     * @param roomId the room id.
     */
    public void sendResultEmails(long roomId) {
        List<Result> results = resultRepository.findAllByRoomId(roomId);
        if (results.size() == 0) {
            System.out.println("Nie ma jeszcze wyników dla tego przedmiotu.");
            return;
        }
        for (Result result : results) {
            System.out.println("Sending email to "
                    + result.getUser().getEmail()
                    + " for term "
                    + result.getTerm().getDay()
                    + " "
                    + result.getTerm().getStartTime());
            mailService.send(result.getUser().getEmail(),
                    "[Plan AGH] Twój plan już jest dla przedmiotu "
                            + result.getRoom().getName()
                            + "!", "Otrzymany przez Ciebie termin to "
                            + Utils.getDayInPolish(result.getTerm().getDay())
                            + " "
                            + result.getTerm().getStartTime()
                            + " - "
                            + result.getTerm().getEndTime()
                            + "!");
        }
    }

    // checking every minute
    @Scheduled(cron = "0 * * * * *")
    public void runAlgorithmIfRoomDeadlinePassed() {
        List<Room> rooms = roomRepository.findAll();
        System.out.println("Checking rooms deadlines");
        for (Room room : rooms) {
            if (!room.getFinished()
                    && (room.getDeadlineDate()
                    .toLocalDate()
                    .isBefore(java.time.LocalDate.now())
                    || room.getDeadlineDate()
                    .toLocalDate()
                    .isEqual(java.time.LocalDate.now()))
                    && room.getDeadlineTime()
                    .isBefore(java.time.LocalTime.now())) {
                System.out.println("Votes in room " + room.getId() + ": " + room.getVotes().size());
                runAlgorithm(room.getId(), -1);
                room.setFinished(true);
                roomRepository.save(room);
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

    private final CommentRepository commentRepository;

    public RoomUsersPreferencesDto getRoomPreferences(long roomId) {
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(
                        () -> new RoomNotFoundException("Room with id: "
                                + roomId
                                + " not found.")
                );

        Set<UserDataDto> users = new HashSet<>();
        Map<Long, UserPreferences> userPreferencesMap = new HashMap<>();

        for (User user : room.getJoinedUsers()) {
            users.add(new UserDataDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()));
            List<Long> selectedTerms = new ArrayList<>();
            List<CommentDto> commentsDto = new ArrayList<>();

            for (Vote vote : room.getVotes()) {
                if (user.equals(vote.getUser())) {
                    selectedTerms.add(vote.getTerm().getId());
                }
            }

            List<Comment> comments = commentRepository.findAllByRoomIdAndUserId(roomId, user.getId());
            for (Comment comment : comments) {
                commentsDto.add(new CommentDto(comment.getTerm().getId(), comment.getContent()));
            }

            userPreferencesMap.put(user.getId(), new UserPreferences(selectedTerms, commentsDto));
        }

        return new RoomUsersPreferencesDto(
                roomId,
                users,
                userPreferencesMap);
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
    private Room getRoom(final Long id) {
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

    public Boolean isFinished(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(""));
        return room.getFinished();
    }
}
