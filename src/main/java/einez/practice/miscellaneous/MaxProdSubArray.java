package einez.practice.miscellaneous;

import java.util.Deque;
import java.util.LinkedList;

public class MaxProdSubArray {
    void mergeOffer(int num, Deque<Integer> q) {
        if (num == 0) {
            if (q.peekLast() != 0) {
                q.offerLast(0);
            }
            return;
        }
        int last = q.peekLast();
        if (last == 0) {
            q.offer(num);
            return;
        }

        if (num > 0) {
            if (last > 0) {
                q.pollLast();
                q.offer(num * last);
            } else {
                q.offer(num);
            }
        } else {
            q.pollLast();
            if (last < 0) {
                if (!q.isEmpty() && q.peekLast() > 0) {
                    q.offerLast(q.pollLast() * last * num);
                } else {
                    q.offerLast(last * num);
                }
            } else {
                if (!q.isEmpty() && q.peekLast() < 0) {
                    int neg = q.pollLast();
                    if (!q.isEmpty() && q.peekLast() > 0) {
                        q.offerLast(q.pollLast() * neg * last * num);
                    } else {
                        q.offerLast(neg * last * num);
                    }
                } else {
                    q.offerLast(last);
                    q.offerLast(num);
                }
            }
        }
    }

    public int maxProduct(int[] nums) {
        Deque<Integer> q = new LinkedList<>();
        int max = nums[0];
        q.offer(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            mergeOffer(nums[i], q);
            max = Math.max(max, q.peekLast());
        }
        q = new LinkedList<>();
        q.offer(nums[nums.length - 1]);
        for (int i = nums.length - 2; i >= 0; i--) {
            mergeOffer(nums[i], q);
            max = Math.max(max, q.peekLast());
        }
        return max;
    }

    public static void main(String[] args) {
        new MaxProdSubArray().maxProduct(new int[]{1, 2, -1, -2, 2, 1, -2, 1, 4, -5, 4});
    }
}
