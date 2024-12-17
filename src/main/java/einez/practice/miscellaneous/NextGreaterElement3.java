package einez.practice.miscellaneous;

public class NextGreaterElement3 {
    public static void main(String[] args) {
        new NextGreaterElement3().nextGreaterElement(12);
    }

    public int nextGreaterElement(int n) {
        if (n < 10) {
            return -1;
        }
        int[] digits = new int[10];
        int high = 9;
        while (n > 0) {
            digits[high] = n % 10;
            high--;
            n /= 10;
        }
        int modify = 9;
        while (modify - 1 > high && digits[modify] <= digits[modify - 1]) {
            modify--;
        }
        if (modify - 1 == high) {
            return -1;
        }
        int pos = digits[modify - 1];
        while (modify <= 9 && digits[modify] > pos) {
            digits[modify - 1] = digits[modify];
            modify++;
        }
        digits[modify - 1] = pos;
        return convert(digits, high);
    }

    private int convert(int[] digits, int high) {
        if (high == -1 && digits[0] > 2) {
            return -1;
        }
        int ans = 0;
        for (int i = high + 1; i < 10; i++) {
            ans = ans * 10 + digits[i];
        }
        if (high > -1) {
            return ans;
        }
        if (ans > (Integer.MAX_VALUE - digits[9]) / 10) {
            return -1;
        }
        return ans * 10 + digits[9];
    }
}
