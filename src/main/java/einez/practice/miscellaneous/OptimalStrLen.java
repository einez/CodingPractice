package einez.practice.miscellaneous;

public class OptimalStrLen {
    static long getOptimalStringLength(int countA, int countB, int maxA, int maxB) {
        if (countA == 0 || maxA == 0) {
            return Math.min(countB, maxB);
        }
        if (countB == 0 || maxB == 0) {
            return Math.min(countA, maxA);
        }
        // min segments, each segment has `maxA` 'A's, all segments have `countA` 'A's in total
        long segA1 = (countA + maxA - 1) / maxA;
        // max segments, each segment has 1 'A'
        long segA2 = countA;
        long segB1 = (countB + maxB - 1) / maxB;
        long segB2 = countB;
        if (segA2 < segB1) {
            return segA2 + Math.min(segB2, (segA2 + 1) * maxB);
        }
        if (segB2 < segA1) {
            return segB2 + Math.min(segA2, (segB2 + 1) * maxA);
        }
        return segA2 + segB2;
    }

    public static void main(String[] args) {
        int[] input;
        int expected;
        long actual;

        input = new int[]{2, 4, 2, 1};
        expected = 5;
        actual = getOptimalStringLength(input[0], input[1], input[2], input[3]);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expected);

        input = new int[]{0, 0, 2, 1};
        expected = 0;
        actual = getOptimalStringLength(input[0], input[1], input[2], input[3]);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expected);

        input = new int[]{0, 2, 1, 0};
        expected = 0;
        actual = getOptimalStringLength(input[0], input[1], input[2], input[3]);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expected);

        input = new int[]{2, 2, 1, 1};
        expected = 4;
        actual = getOptimalStringLength(input[0], input[1], input[2], input[3]);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expected);

        input = new int[]{8, 64, 2, 2};
        expected = 26;
        actual = getOptimalStringLength(input[0], input[1], input[2], input[3]);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expected);

        input = new int[]{8, 26, 2, 3};
        expected = 34;
        actual = getOptimalStringLength(input[0], input[1], input[2], input[3]);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expected);
    }
}
