package einez.practice.miscellaneous;

import einez.practice.ArrayConverter;

import java.util.PriorityQueue;
import java.util.Queue;

public class NonOverlappingIntervals {
    public static void main(String[] args) {
        int[][] intervals = ArrayConverter.convert2("[[1,2],[2,3],[3,4],[1,3]]");
        new NonOverlappingIntervals().eraseOverlapIntervals(intervals);
    }
    public int eraseOverlapIntervals(int[][] intervals) {
        Queue<int[]> pq = new PriorityQueue<>((i1, i2) -> {
            if (i1[0] == i2[0]) {
                return i2[1] - i1[1];
            }
            return i1[0] - i2[0];
        });
        for (int[] e: intervals) {
            pq.offer(e);
        }
        int rm = 0;
        int[] pre = pq.poll();
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (pre[1] <= cur[0]) {
                pre = cur;
                continue;
            }
            rm++;
            if (pre[0] == cur[0] || pre[1] >= cur[1]) {
                pre = cur;
            }
        }
        return rm;
    }
}
