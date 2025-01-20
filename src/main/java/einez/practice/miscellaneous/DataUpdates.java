package einez.practice.miscellaneous;

import java.util.Arrays;

public class DataUpdates {
    public static void updateData(int[] data, int[][] updates) {
        int[] helper = new int[data.length + 1];
        Arrays.fill(helper, 1);
        for (int[] update : updates) {
            int left = update[0];
            int right = update[1];
            helper[left - 1] *= -1;
            helper[right] *= -1;
        }
        int factor = 1;
        for (int i = 0; i < data.length; i++) {
            factor *= helper[i];
            data[i] *= factor;
        }
    }

    public static void main(String[] args) {
        int[] data = new int[]{1, -4, -5, 2};
        int[][] updates = new int[][]{
                {2, 4},
                {1, 2}
        };
        int[] expected = new int[]{-1, -4, 5, -2};
        updateData(data, updates);
        System.out.printf("updated = %s, assert = %b%n", Arrays.toString(data), assertEqual(data, expected));

        data = new int[100];
        Arrays.fill(data, 1);
        updates = new int[][]{
                {2, 100},
        };
        expected = new int[100];
        Arrays.fill(expected, -1);
        expected[0] = 1;
        updateData(data, updates);
        System.out.printf("updated = %s, assert = %b%n", Arrays.toString(data), assertEqual(data, expected));

        updates = new int[][]{
                {2, 10},
                {10, 100},
        };
        Arrays.fill(expected, 1);
        expected[9] = -1;
        updateData(data, updates);
        System.out.printf("updated = %s, assert = %b%n", Arrays.toString(data), assertEqual(data, expected));
    }

    private static boolean assertEqual(int[] actual, int[] expected) {
        for (int i = 0; i < actual.length; i++) {
            if (actual[i] != expected[i]) {
                return false;
            }
        }
        return true;
    }
}
