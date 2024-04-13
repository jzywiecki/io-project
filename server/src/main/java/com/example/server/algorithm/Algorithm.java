package com.example.server.algorithm;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class Algorithm {
    /** Number of available terms. */
    private final int termsNum;
    /** Students' preferences as a map
     * from student_ids to arrays of terms_ids.
     */
    private final Map<Integer, int[]> choices;
    /**
     * Initial temperature for simulated annealing.
     * Determines how many iterations the algorithm will perform.
     */
    static final double INITIAL_T = 30_000;
    /**
     * Determines the curve of temperature's decrease
     * and therefore how many iterations the algorithm will perform.
     */
    static final double STEP_DIVISION_T = 1.0001;
    /** The temperature cutoff, the algorithm stops after it's reached. */
    static final double MIN_T = 0.0001;
    /** How many random changes are tried for each achieved temperature. */
    static final int INNER_ITER_COUNT = 10;
    /** How hard is stepping over the term limit punished.*/
    static final int PUNISHMENT = 10;

    /** Constructor for the algorithm.
     * @param terms numbers of available terms
     * @param choiceMap a map from student id to an array
     *                  of term ids (their preferences)
     */
    public Algorithm(final int terms, final Map<Integer, int[]> choiceMap) {
        this.termsNum = terms;
        this.choices = choiceMap;
    }

    /**
     * Runs the algorithm with parameters as they currently are.
     * @return An array arr where arr[student_id] = assigned_term_id.
     */
    public final int[] run() {
        return this.run(-1);
    }
    /**
     * Runs the algorithm with parameters as they currently are.
     * @param maxTermsNum maximum number of terms to assign people to.
     *                    -1 means no limit.
     * @return An array arr where arr[student_id] = assigned_term_id.
     */
    public final int[] run(final int maxTermsNum) {
        Random random = new Random();
        int studentsNum = this.choices.size();
        int[] assignment = new int[studentsNum];

        for (int i = 0; i < assignment.length; i++) {
            assignment[i] = random.nextInt(0, termsNum);
        }

        double energy = this.metric(assignment, maxTermsNum);

        int[] bestAssignment = copyArray(assignment);
        double lowestEnergy = energy;

        double t = INITIAL_T;
        double newEnergy;
        while (t > MIN_T) {
            for (int i = 0; i < INNER_ITER_COUNT; i++) {
                int pos = random.nextInt(0, studentsNum);
                int newVal = random.nextInt(0, termsNum);
                int oldVal = assignment[pos];

                assignment[pos] = newVal;
                newEnergy = this.metric(assignment, maxTermsNum);
                // maybe necessary:
                // newEnergy = newEnergy == 0 ? newEnergy + 0.00001 : newEnergy

                if (newEnergy < energy) {
                    energy = newEnergy;
                    if (energy < lowestEnergy) {
                        // interesting results without substituting best energy
                        lowestEnergy = energy;
                        bestAssignment = copyArray(assignment);
                    }
                } else {
                    double probability = Math.exp(-(newEnergy - energy) / t);
                    double rand = Math.random();
                    // Used to be >, but <= gives way better
                    // results with maxTermsNum != -1
                    if (Math.sqrt(rand) <= probability) {
                        assignment[pos] = oldVal;
                    }
                }
            }

            t /= STEP_DIVISION_T;
        }
        // Performs a swap in case of:
        // student a's preferences: [x, ...]
        // student a got: y
        // student b's preferences: [y, ...]
        // student b got: x
        for (int studentID = 0; studentID < assignment.length; studentID++) {
            int assignedTo = assignment[studentID];

            int[] studentChoices = choices.get(studentID);
            if (!arrayContains(studentChoices, assignedTo)) {
                for (int sndStudentId = studentID + 1;
                     sndStudentId < assignment.length; sndStudentId++) {
                    int sndAssignedTo = assignment[sndStudentId];
                    int[] sndStudentChoices = choices.get(sndStudentId);
                    if ((!arrayContains(sndStudentChoices, sndAssignedTo))
                            && arrayContains(sndStudentChoices, assignedTo)
                            && arrayContains(studentChoices, sndAssignedTo)) {
                        assignment[studentID] = sndAssignedTo;
                        assignment[sndStudentId] = assignedTo;
                    }
                }
           }
        }

        return bestAssignment;
    }

    private double metric(final int[] assignment, final int maxTermsNum) {
        int[] termStats = new int[termsNum];
        int gotPreferred = 0;
        int possibleSwaps = 0;

        for (int studentID = 0;
             studentID < assignment.length; studentID++) {
            int assignedTo = assignment[studentID];
            termStats[assignedTo]++;

            int[] studentChoices = choices.get(studentID);
            if (arrayContains(studentChoices, assignedTo)) {
                gotPreferred++;
            } else {
                for (int sndStudentId = studentID + 1;
                     sndStudentId < assignment.length; sndStudentId++) {
                    int sndAssignedTo = assignment[sndStudentId];
                    int[] sndStudentChoices = choices.get(sndStudentId);
                    if ((!arrayContains(sndStudentChoices, sndAssignedTo))
                            && arrayContains(sndStudentChoices, assignedTo)
                            && arrayContains(studentChoices, sndAssignedTo)) {
                        possibleSwaps += 2;
                    }
                }
            }
        }
        int delta = getDelta(termStats, maxTermsNum);

        return (double) delta
               + ((double) possibleSwaps - (double) gotPreferred)
               / (double) choices.size();

//        return switch (maxTermsNum) {
//            case TWO -> (double) delta
//                    + (double) possibleSwaps - Math.sqrt(gotPreferred);
//            case THREE -> ((double) delta - (double) gotPreferred)
//                    / (double) choices.size()
//                    + (double) possibleSwaps
//                    / (double) (termsNum + choices.size());
//            case -1 ->
//            default ->
//
//        };
//
    }
    private static boolean arrayContains(final int[] array, final int value) {
        for (int j : array) {
            if (j == value) {
                return true;
            }
        }
        return false;
    }

    private static int getDelta(final int[] array, final int maxTermsNum) {
        int min = Integer.MAX_VALUE;
        int max = 0;
        if (maxTermsNum == -1) {
            for (int j : array) {
                min = Integer.min(min, j);
                max = Integer.max(max, j);
            }
        } else {
            int[] copied = copyArray(array);
            Arrays.sort(copied);
            for (int i = 0;
                 i < Integer.divideUnsigned(copied.length, 2);
                 i++) {
                int temp = copied[i];
                copied[i] = copied[copied.length - 1 - i];
                copied[copied.length - 1 - i] = temp;
            }
            for (int i = 0; i < Math.min(maxTermsNum, copied.length); i++) {
                min = Integer.min(min, copied[i]);
                max = Integer.max(max, copied[i]);
            }
            for (int i = maxTermsNum; i < copied.length; i++) {
                max += copied[i] * PUNISHMENT;
            }
        }
        return max - min;
    }

    private static int[] copyArray(final int[] array) {
        int[] copied = new int[array.length];
        System.arraycopy(array, 0, copied, 0, array.length);
        return copied;
    }

}
