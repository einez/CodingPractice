package einez.practice.backtracking;

/**
 * @see <a href="https://www.hackerrank.com/challenges/ctci-recursive-staircase/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=recursion-backtracking">hackerrank</a>
 */
public class DavisStaircase {
    static class Result {
        public static int stepPerms(int n) {
            // Write your code here
            // f(n) = f(n - 1) + f(n - 2) + f(n - 3)
            if (n == 1) {
                return 1;
            }
            if (n == 2) {
                return 2;
            }
            if (n == 3) {
                return 4;
            }
            long a = 1;
            long b = 2;
            long c = 4;
            long d;
            for (int i = 4; i <= n; i++) {
                d = a + b + c;
                d %= 10000000007L;
                a = b;
                b = c;
                c = d;
            }
            return (int)c;
        }
    }
}
