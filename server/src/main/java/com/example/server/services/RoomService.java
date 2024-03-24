package com.example.server.services;

import com.example.server.algorithm.Algorithm;
import com.example.server.repositories.ResultRepository;
import com.example.server.model.Term;
import com.example.server.model.Room;
import com.example.server.model.User;
import com.example.server.model.Result;
import com.example.server.model.Vote;
import com.example.server.repositories.RoomRepository;
import com.example.server.repositories.TermRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Arrays;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class RoomService {
    /** Room repository. */
    private final RoomRepository roomRepository;
    /** Term repository. */
    private final TermRepository termRepository;
    /** Result repository. */
    private final ResultRepository resultRepository;
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
//
//
//    }
}
