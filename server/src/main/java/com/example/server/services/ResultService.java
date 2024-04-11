package com.example.server.services;

import com.example.server.dto.ResultDto;
import com.example.server.dto.ResultsDto;
import com.example.server.dto.TermDto;
import com.example.server.dto.UserResultsDto;
import com.example.server.exceptions.DeadlineHasNotPassedException;
import com.example.server.exceptions.ResultNotFoundException;
import com.example.server.exceptions.RoomNotFoundException;
import com.example.server.exceptions.UserNotFoundException;
import com.example.server.model.Result;
import com.example.server.model.Room;
import com.example.server.repositories.ResultRepository;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ResultService {
    /**
     * Result repository.
     */
    private final ResultRepository resultRepository;
    /**
     * Room repository.
     */
    private final RoomRepository roomRepository;
    /**
     * User repository.
     */
    private final UserRepository userRepository;

    /**
     * Get the results of all users in a room.
     * @param roomId the room id.
     * @return the results of all users in a room.
     */
    public ResultsDto getResults(final long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new RoomNotFoundException("Room not found."));
        checkDateDeadline(roomId);

        List<Result> resultsList = resultRepository.findAllByRoomId(roomId);

        List<ResultDto> results = new ArrayList<>();

        for (Result result : resultsList) {

            TermDto termDto = TermDto
                    .builder()
                    .day(result.getTerm().getDay())
                    .startTime(result.getTerm().getStartTime())
                    .endTime(result.getTerm().getEndTime())
                    .build();

            ResultDto resultDto = ResultDto
                    .builder()
                    .result(termDto)
                    .firstName(result.getUser().getFirstName())
                    .lastName(result.getUser().getLastName())
                    .email(result.getUser().getEmail())
                    .build();

            results.add(resultDto);
        }

        return ResultsDto
                .builder()
                .roomId(roomId)
                .roomName(room.getName())
                .roomDescription(room.getDescription())
                .results(results)
                .build();
    }

    /**
     * Get the results of a specified user in a room.
     * @param roomId the room id.
     * @param userId the user id.
     * @return the results of a specified user in a room.
     */
    public UserResultsDto getUserResults(final long roomId, final long userId) {
        checkDateDeadline(roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new RoomNotFoundException("Room not found."));
        userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found."));

        Result result = resultRepository
                .findByRoomIdAndUserId(roomId, userId)
                .orElseThrow(
                    () -> new ResultNotFoundException("Result not found.")
                );

        TermDto termDto = TermDto
                .builder()
                .day(result.getTerm().getDay())
                .startTime(result.getTerm().getStartTime())
                .endTime(result.getTerm().getEndTime())
                .build();

        return UserResultsDto
                .builder()
                .roomId(roomId)
                .roomName(room.getName())
                .roomDescription(room.getDescription())
                .totalVotes(room.getJoinedUsers().size())
                .result(termDto)
                .build();

    }

    private void checkDateDeadline(final long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new RoomNotFoundException("Room not found."));

        boolean isAfterToday = room.getDeadlineDate()
                                   .toLocalDate()
                                   .isAfter(LocalDateTime.now().toLocalDate());

        if (room.getFinished()) {
            return;
        }

        if (!isAfterToday) {
            boolean isToday = room.getDeadlineDate()
                                  .toLocalDate()
                                  .isEqual(LocalDateTime.now().toLocalDate());
            if (isToday) {
                boolean isAfterNow = room.getDeadlineTime()
                                         .isAfter(LocalDateTime.now()
                                                        .toLocalTime());
                if (isAfterNow) {
                    throw new DeadlineHasNotPassedException(
                            "The deadline has not passed yet.");
                }
            }
        }
    }
}
