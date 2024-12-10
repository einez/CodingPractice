package einez.practice.backtracking.easy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.IntUnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FibonacciNumbersTest {
    IntUnaryOperator method = FibonacciNumbers.Solution::fibonacci;

    @Test
    @DisplayName("Compare with naive")
    void fibonacci() {
        for (int i = 0; i < 10; i++) {
            assertEquals(naiveFibo(i), method.applyAsInt(i));
        }
    }

    private static int naiveFibo(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        return naiveFibo(n - 1) + naiveFibo(n - 2);
    }
}