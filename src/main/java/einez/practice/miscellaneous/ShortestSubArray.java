package einez.practice.miscellaneous;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortestSubArray {
    public static void main(String[] args) {
        int[] nums = new int[]{48, 99, 37, 4, -31};
        new ShortestSubArray().shortestSubarray(nums, 140);
    }

    public int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        int minLen = n + 1;
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0, startIndex = 0, curSum = 0; i < nums.length; i++) {
            if (curSum <= 0) {
                startIndex = i;
                curSum = nums[i];
            } else {
                curSum += nums[i];
                if (nums[i] < 0) {
                    pq.offer(new int[]{curSum, i + 1});
                }
            }
            if (curSum < k) {
                continue;
            }
            int sub = nums[startIndex];
            startIndex++;
            while (!pq.isEmpty() && curSum - pq.peek()[0] >= k) {
                int[] p = pq.poll();
                if (startIndex < p[1]) {
                    startIndex = p[1];
                    sub = p[0];
                }
            }
            while (curSum - sub >= k && startIndex <= i) {
                sub += nums[startIndex];
                startIndex++;
            }
            for (Iterator<int[]> it = pq.iterator(); it.hasNext(); ) {
                int[] e = it.next();
                if (e[1] < startIndex) {
                    it.remove();
                } else {
                    e[0] += sub;
                }
            }
            curSum -= sub;
            minLen = Math.min(minLen, i - startIndex + 2);
        }
        return minLen == n + 1 ? -1 : minLen;
    }
}
