package com.example.server.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

public class AlgorithmTests {

    @Test
    void solvesSimpleCase() {
        Map<Integer, int[]> choices = new HashMap<>();
        choices.put(0, new int[]{3});
        choices.put(1, new int[]{2});
        choices.put(2, new int[]{1});
        choices.put(3, new int[]{0});
        int termsNum = 4;
        Algorithm alg = new Algorithm(termsNum, choices);
        int[] assignment = alg.run();
        assertTrue(
                assignment[0] == 3
                        && assignment[1] == 2
                        && assignment[2] == 1
                        && assignment[3] == 0
        );
    }

    @Test
    void findsOptimalWithConstrains() {
        Map<Integer, int[]> choices = new HashMap<>();
        choices.put(0, new int[]{0, 1});
        choices.put(1, new int[]{2, 3});
        choices.put(2, new int[]{4, 9});
        choices.put(3, new int[]{0, 5});
        choices.put(4, new int[]{2, 7});
        choices.put(5, new int[]{4, 8});
        int termsNum = 10;
        Algorithm alg = new Algorithm(termsNum, choices);

        int gotItRight = 0;
        int[] assignment;
        for (int j = 0; j < 5; j++) {
            assignment = alg.run(3);
            if (
                    assignment[0] == 0
                    && assignment[1] == 2
                    && assignment[2] == 4
                    && assignment[3] == 0
                    && assignment[4] == 2
                    && assignment[5] == 4
            ) gotItRight++;
        }
        assertTrue(gotItRight >= 1);
    }

    @Test
    void okResultsForComplexCase() {
        int studentsNum = 6;
        int gotPreferred = 0;
        int termsNum = 5;

        Map<Integer, int[]> choices = new HashMap<>();
        choices.put(0, new int[]{0, 2, 4, 6});
        choices.put(1, new int[]{2, 4});
        choices.put(2, new int[]{1, 7, 8, 9});
        choices.put(3, new int[]{6, 8});
        choices.put(4, new int[]{4, 5, 9});
        choices.put(5, new int[]{1, 3});
        Algorithm alg = new Algorithm(termsNum, choices);
        int[] results = alg.run(3);
        Set<Integer> termsUsed = new HashSet<>();
        for (int i = 0; i < studentsNum; i++) {
            termsUsed.add(results[i]);
            if (Arrays.asList(Arrays.stream(choices.get(i)).boxed().toArray(Integer[]::new)).contains(results[i])) gotPreferred++;
        }
        assertTrue(gotPreferred >= 3);
        assertEquals(3, termsUsed.size());
    }
}
