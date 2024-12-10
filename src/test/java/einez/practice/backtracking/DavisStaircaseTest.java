package einez.practice.backtracking;

import org.junit.jupiter.api.Test;

import java.util.function.IntUnaryOperator;

import static org.junit.jupiter.api.Assertions.*;

class DavisStaircaseTest {
    IntUnaryOperator permutation = DavisStaircase.Result::stepPerms;

    @Test
    void stepPerms() {
        int[][] testCases = new int[][]{
                {1, 1},
                {2, 2},
                {3, 4},
                {4, 7},
                {5, 13},
                {6, 24},
                {7, 44},
                {8, 81},
                {15, 5768},
                {20, 121415},
                {27, 8646064},
        };
        for (int[] test : testCases) {
            assertEquals(test[1], permutation.applyAsInt(test[0]));
        }
    }
}