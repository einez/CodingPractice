package einez.practice.pramp;

public class RootOfNum {
    static double root(double x, int n) {
        // your code goes here
        if (x == 0) {
            return 0f;
        }
        if (x == 1) {
            return 1f;
        }
        double floor = x > 1 ? 1f : x;
        double ceiling = x > 1 ? x : 1f;
        double mid = 1f;
        while (true) {
            mid = (floor + ceiling) / 2;
            double power = Math.pow(mid, n);
            if (power == x || Math.abs(power - x) < 0.001) {
                return mid;
            }
            if (power < x) {
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
    }
}
