package einez.practice.backtracking.easy;

/**
 * @see <a href="https://www.hackerrank.com/challenges/ctci-fibonacci-numbers/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=recursion-backtracking">hackerrank</a>
 */
public class FibonacciNumbers {
    public static class Solution {
        public static int fibonacci(int n) {
            if (n <= 1) {
                return n;
            }
            int a = 0;
            int b = 1;
            for (int i = 2, c; i <= n; i++) {
                c = a + b;
                a = b;
                b = c;
            }
            return b;
        }
    }
}
