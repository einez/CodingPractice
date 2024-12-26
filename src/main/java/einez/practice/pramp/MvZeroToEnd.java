package einez.practice.pramp;

public class MvZeroToEnd {
    public static int[] moveZerosToEnd(int[] arr) {
        // your code goes here
        int n = arr.length;
        if (n < 2) {
            return arr;
        }
        int count0 = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0) {
                count0++;
                continue;
            }
            int tofill = i - count0;
            arr[tofill] = arr[i];
        }
        for (int i = n - count0; i < n; i++) {
            arr[i] = 0;
        }
        return arr;
    }

    public static int[] moveZerosToStart(int[] arr) {
        // your code goes here
        int n = arr.length;
        if (n < 2) {
            return arr;
        }
        int right = n - 1;
        int count0 = 0;
        while (right >= 0) {
            if (arr[right] != 0) {
                arr[right + count0] = arr[right];
            } else {
                count0++;
            }
            right--;
        }
        while (count0 > 0) {
            arr[count0 - 1] = 0;
            count0--;
        }
        return arr;
    }

    public static void main(String[] args) {
        moveZerosToEnd(new int[]{1, 0, 2});
    }
}
