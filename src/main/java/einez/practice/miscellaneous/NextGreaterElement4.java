package einez.practice.miscellaneous;

import java.util.Arrays;
import java.util.Stack;

public class NextGreaterElement4 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 17, 18, 0, 18, 10, 20, 0};
        new NextGreaterElement4().secondGreaterElement(nums);
    }

    public int[] secondGreaterElement(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Stack<Integer> next = new Stack<>();
        Stack<Integer> secNext = new Stack<>();
        Stack<Integer> tmp = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!next.empty() && nums[next.peek()] < nums[i]) {
                tmp.push(next.pop());
            }
            next.push(i);
            if (!tmp.empty()) {
                int h = tmp.pop();
                int sn = tmp.empty() ? nums[i] : nums[tmp.peek()];
                while (!secNext.empty() && nums[secNext.peek()] < sn) {
                    ans[secNext.pop()] = sn;
                }
                while (!secNext.empty()) {
                    ans[secNext.pop()] = nums[i];
                }
                secNext.push(h);
                while (!tmp.empty()) {
                    secNext.push(tmp.pop());
                }
            }
        }
        if (next.size() == 1) {
            return ans;
        }
        while (next.size() > 2) {
            next.pop();
        }
        int val = nums[next.pop()];
        while (!secNext.empty() && nums[secNext.peek()] < val) {
            ans[secNext.pop()] = val;
        }

        return ans;
    }
}
