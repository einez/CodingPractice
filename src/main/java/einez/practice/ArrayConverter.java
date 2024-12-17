package einez.practice;

import java.util.Arrays;

public class ArrayConverter {
    public static int[][] convert2(String s) {
        String[] rows = s.substring(2, s.length() - 2).split("],\\[");
        int[][] ans = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            ans[i] = parse(rows[i]);
        }
        return ans;
    }

    private static int[] parse(String nums) {
        return Arrays.stream(nums.trim().split(","))
                .mapToInt(Integer::parseInt).toArray();
    }
}
