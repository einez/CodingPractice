package einez.practice.nmock;

import java.util.*;

public class Counter {
    private final int[] countByTime;

    // Could use a helper to keep record of next timestamp that is put data in case it is sparse, thereby avoiding iterate the whole array
    // private final int[] nextTimestamp;

    private long lastStartTime;
    private long lastEndTime;

    // List could be optimized to a circle array, just like the int[] countByTime
    private final Map<String, List<Long>> key2timestamps;
    private final int capacity;

    Counter(int sec) {
        capacity = sec;
        countByTime = new int[capacity];
        lastStartTime = 0L;
        lastEndTime = 0L;
        key2timestamps = new HashMap<>(capacity);
    }

    private long getStartTime() {
        return getCurrentTime() - capacity + 1;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    void put(String key) {
        long curTime = getCurrentTime();
        key2timestamps.computeIfAbsent(key, k -> new ArrayList<>()).add(curTime);

        // invalidate staled data
        long curStart = getStartTime();
        lastStartTime = erase(countByTime, lastStartTime, lastEndTime, curStart, curTime);
        lastEndTime = curTime;
        countByTime[(int) (curTime % capacity)] += 1;
    }

    int get_total_count() {
        long curTime = getCurrentTime();
        lastStartTime = erase(countByTime, lastStartTime, lastEndTime, getStartTime(), curTime);
        lastEndTime = curTime;
        return count(countByTime, lastStartTime, lastEndTime);
    }

    private static long erase(int[] counter, long start1, long end1, long start2, long end2) {
        // same as the last time, no need to update
        if (end2 == end1) {
            return start1;
        }

        // clear all
        if (start2 > end1) {
            if (start1 == end1) {
                counter[(int) (start1 % counter.length)] = 0;
            } else {
                Arrays.fill(counter, 0);
            }
            return end2;
        }

        int n = counter.length;

        // from s1 to s2, set 0
        for (long i = start1; i < start2; i++) {
            counter[(int) (i % n)] = 0;
        }

        // compress s2 to the 1st non-zero before e2
        for (long i = Math.max(start1, start2); i < end2; i++) {
            if (counter[(int) (i % n)] != 0) {
                return i;
            }
        }

        return end2;
    }

    private static int count(int[] counter, long start, long end) {
        int small = (int) (start % counter.length);
        int large = (int) (end % counter.length);
        int ans = 0;
        if (small <= large) {
            for (int i = small; i <= large; i++) {
                ans += counter[i];
            }
        } else {
            for (int i = 0; i <= large; i++) {
                ans += counter[i];
            }
            for (int i = small; i < counter.length; i++) {
                ans += counter[i];
            }
        }
        return ans;
    }

    int get_count(String key) {
        List<Long> timestamps = key2timestamps.get(key);
        if (timestamps == null) {
            return 0;
        }

        long startTime = getStartTime();
        if (timestamps.get(timestamps.size() - 1) < startTime) {
            key2timestamps.remove(key);
            return 0;
        }

        int index = Collections.binarySearch(timestamps, startTime);
        if (index < 0) {
            index = -(index + 1);
        }
        moveAndShrinkList(timestamps, index);
        return timestamps.size();
    }

    private static void moveAndShrinkList(List<Long> list, int newStart) {
        if (newStart == 0) {
            return;
        }
        int newSize = list.size() - newStart;
        for (int i = 0; i < newSize; i++) {
            list.set(i, list.get(i + newStart));
        }
        list.subList(newSize, list.size()).clear();
    }
}
