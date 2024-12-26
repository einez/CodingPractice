package einez.practice.nmock;

import java.util.*;

public class Counter2 {
    static class MyQueue {
        Deque<CountAtTime> deque;
        int sum;

        MyQueue() {
            this.sum = 0;
            this.deque = new ArrayDeque<>();
        }

        int getCountAndClean(long from) {
            while (!deque.isEmpty() && deque.peekFirst().timestamp < from) {
                sum -= deque.pollFirst().count;
            }
            return sum;
        }

        void addCount(long timestamp) {
            if (deque.isEmpty() || deque.peekFirst().timestamp != timestamp) {
                deque.offerLast(new CountAtTime(timestamp));
            } else {
                deque.peekLast().count += 1;
            }
            sum++;
        }
    }

    static class CountAtTime {
        int count;
        long timestamp;

        CountAtTime(long timestamp) {
            this.count = 1;
            this.timestamp = timestamp;
        }
    }

    private final int second;
    private final MyQueue totalQueue;
    private final Map<String, MyQueue> map;

    Counter2(int sec) {
        this.second = sec;
        this.totalQueue = new MyQueue();
        this.map = new HashMap<>();
    }

    private long validStartTime() {
        return getCurrentTime() - second + 1;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    void put(String key) {
        long curTime = getCurrentTime();
        this.totalQueue.addCount(curTime);
        this.map.computeIfAbsent(key, k -> new MyQueue()).addCount(curTime);
    }

    int get_total_count() {
        return this.totalQueue.getCountAndClean(validStartTime());
    }

    int get_count(String key) {
        MyQueue kq = map.get(key);
        if (kq == null) {
            return 0;
        }
        int count = kq.getCountAndClean(validStartTime());
        if (count == 0) {
            map.remove(key);
        }
        return count;
    }
}
