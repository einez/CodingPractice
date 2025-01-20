package einez.practice.miscellaneous;

public class SumOfAllValues {
    public static void main(String[] args) {
        String num;
        int expect;
        int actual = 0;

        num = "123";
        expect = 168;
        // actual = sumOfAllValues(num);
        System.out.printf("expect = %d, assert = %b%n", expect, expect == actual);
    }

    private static final int MOD = 1000000007;

//    private static int sumOfAllValues(String num) {
//        int[] digits = new int[num.length()];
//        for (int i = 0; i < digits.length; i++) {
//            digits[i] = num.charAt(i) - '0';
//        }
//        return sumOfAllValues(digits);
//    }
//
//    private static int sumOfAllValues(int[] digits) {
//        int ans = 0;
//        for (int i = 0; i < digits.length; i++) {
//            int m = Math.max(i, 1);
//            int n = Math.min(digits.length - i - 1, 1);
//        }
//    }
//
//    private static int calculate(int power2) {
//        int v = 1;
//        for (int j = 0; j < power2; j++) {
//            v <<= 1;
//            v %= MOD;
//        }
//        return (v + 1) % MOD;
//    }
}
