package com.example.server.dto;

import com.example.server.model.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResultsDto {
    /**
     * Result for the user.
     */
    Term result;
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
