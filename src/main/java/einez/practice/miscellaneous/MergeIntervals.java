package einez.practice.miscellaneous;

import einez.practice.ArrayConverter;

import java.util.*;

public class MergeIntervals {
    public static void main(String[] args) {
//        int[][] intervals = ArrayConverter.convert2("[[1,3],[2,6],[8,10],[15,18]]");
        int[][] intervals = ArrayConverter.convert2("[[2,3],[5,5],[2,2],[3,4],[3,4]]");
        new MergeIntervals().merge(intervals);
        StringBuilder sb = new StringBuilder();
    }

    public int[][] merge(int[][] intervals) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int[] i : intervals) {
            for (Map.Entry<Integer, Integer> entry = map.floorEntry(i[0]); entry != null && entry.getValue() >= i[0]; ) {
                map.remove(entry.getKey());
                i[0] = entry.getKey();
                i[1] = Math.max(i[1], entry.getValue());
                entry = map.lowerEntry(i[0]);
            }
            for (Map.Entry<Integer, Integer> entry = map.higherEntry(i[0]); entry != null && entry.getKey() <= i[1]; ) {
                map.remove(entry.getKey());
                i[1] = Math.max(i[1], entry.getValue());
                entry = map.higherEntry(i[0]);
            }
            map.put(i[0], i[1]);
        }
        int[][] ans = new int[map.size()][];
        int index = 0;
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            ans[index] = new int[]{e.getKey(), e.getValue()};
            index++;
        }
        return ans;
    }

    public int[][] merge0(int[][] intervals) {
        Arrays.sort(intervals, (i1, i2) -> {
            if (i1[0] == i2[0]) {
                return i2[1] - i1[1];
            }
            return i1[0] - i2[0];
        });
        List<int[]> ans = new ArrayList<>();
        int[] pre = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            int[] cur = intervals[i];
            if (pre[1] < cur[0]) {
                ans.add(pre);
                pre = cur;
                continue;
            }
            pre[1] = Math.max(pre[1], cur[1]);
        }
        ans.add(pre);
        int[][] trans = new int[ans.size()][];
        int index = 0;
        for (int[] e : ans) {
            trans[index] = e;
            index++;
        }
        return trans;
    }
}
