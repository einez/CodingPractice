package einez.practice.dp;

import java.util.Arrays;

public class CollectPoints {
    public static void main(String[] args) {
        int n;
        int[] players;
        int m;
        int[] points;
        int expect;
        int actual;

        n = 1;
        players = new int[]{2};
        m = 2;
        points = new int[]{1, 3};
        expect = 3; // 2 -> 1 -> 3
        actual = collect(n, players, m, points);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expect);

        n = 1;
        players = new int[]{2};
        m = 2;
        points = new int[]{4, 1};
        expect = 4; // 2 -> 1 -> 4
        actual = collect(n, players, m, points);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expect);

        n = 2;
        players = new int[]{2, 5};
        m = 3;
        points = new int[]{4, 1, 6};
        expect = 3; // 2 -> 1; 5 -> 4 -> 6
        actual = collect(n, players, m, points);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expect);

        n = 2;
        players = new int[]{2, 5};
        m = 3;
        points = new int[]{4, 1, 8};
        expect = 4; // 2 -> 1 -> 4; 5 -> 8
        actual = collect(n, players, m, points);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expect);

        n = 2;
        players = new int[]{2, 7};
        m = 4;
        points = new int[]{6, 1, 8, 13};
        expect = 6; // 2 -> 1 -> 6; 7 -> 8 -> 13
        actual = collect(n, players, m, points);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expect);

        n = 5;
        players = new int[]{2, 7, 1, 8, 13};
        m = 2;
        points = new int[]{6, 14};
        expect = 1; // 7 -> 6; 13 -> 14
        actual = collect(n, players, m, points);
        System.out.printf("actual = %d, assertion = %b%n", actual, actual == expect);
    }

    public static int collect(int n, int[] players, int m, int[] points) {
        Arrays.sort(players);
        Arrays.sort(points);

        // all points should be collected by the 1st player
        if (points[0] >= players[n - 1]) {
            return points[m - 1] - players[n - 1];
        }
        // all points should be collected by the last player
        if (points[m - 1] <= players[0]) {
            return players[0] - points[0];
        }

        // dp[i][j] is the answer of players[0:i-1] points[0:j-1]
        // so the final answer is dp[n][m], i.e. players[0:n-1] points[0:m-1]
        int[][] dp = new int[n + 1][m + 1];

        fillDp1(players[0], dp[1], points, m);

        for (int i = 2; i <= n; i++) {
            fillDp(dp[i - 1], dp[i], players[i - 1], points, m);
        }

        return dp[n][m];
    }

    private static void fillDp1(int player, int[] dp1, int[] points, int m) {
        int index = Arrays.binarySearch(points, player);
        if (index < 0) {
            index = -(index + 1);
        }

        for (int i = 0; i < index; i++) {
            dp1[i + 1] = player - points[i];
        }
        for (int i = index; i < m; i++) {
            dp1[i + 1] = backforth(player - points[0], points[i] - player);
        }
    }

    private static void fillDp(int[] previous, int[] current, int player, int[] points, int m) {
        int index = Arrays.binarySearch(points, player);
        if (index < 0) {
            index = -(index + 1);
        }

        if (index == 0) {
            for (int i = 0; i < m; i++) {
                current[i + 1] = points[i] - player;
            }
            return;
        }

        int i;
        for (i = 0; i < index && player - points[i] > previous[i + 1]; i++) {
            current[i + 1] = previous[i + 1];
        }
        for (; i < index; i++) {
            current[i + 1] = Math.max(previous[i], player - points[i]);
        }
        for (; i < m; i++) {
            int maxTime = previous[i + 1];
            int bfTime = points[i] - player;
            int newMax = Math.max(bfTime, previous[index]);
            for (int j = index; newMax < maxTime; ) {
                maxTime = newMax;
                j--;
                if (j < 0) {
                    break;
                }
                bfTime = backforth(points[i] - player, player - points[j]);
                newMax = Math.max(bfTime, previous[j]);
            }
            current[i + 1] = maxTime;
        }
    }

    private static int backforth(int side1, int side2) {
        return Math.min(side1, side2) + side1 + side2;
    }
}
