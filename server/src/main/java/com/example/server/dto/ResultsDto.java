package com.example.server.dto;

import com.example.server.model.Term;
import com.example.server.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResultsDto {
    /**
     * The results of the users in the room.
     */
    Map<User, Term> results;
    /**
     * The total number of votes in the room.
     */
    int totalVotes;
    /**
     * The room id.
     */
    long roomId;
    /**
     * The room name.
     */
    String roomName;
    /**
     * The room description.
     */
    String roomDescription;
}
