package einez.practice.miscellaneous;

import java.util.HashMap;
import java.util.Map;

public class LFUCache {
    static class Node {
        int key;
        int val;
        int freq;
        Node pre;
        Node next;
        Node(int k, int v) {
            key = k;
            val = v;
            freq = 1;
        }
    }

    private final int capacity;
    private int size;
    private final Map<Integer, Node> kvMap;
    private final Map<Integer, Node> freqMap;
    private Node tail;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.kvMap = new HashMap(capacity);
        this.freqMap = new HashMap(capacity);
        this.tail = new Node(-1,-1);
    }

    public int get(int key) {
        if (!kvMap.containsKey(key)) {
            return -1;
        }
        Node n = kvMap.get(key);
        increaseFreq(n);
        return n.val;
    }

    public void put(int key, int value) {
        if (kvMap.containsKey(key)) {
            Node node = kvMap.get(key);
            node.val = value;
            increaseFreq(node);
            return;
        }
        if (size == capacity) {
            evict();
        } else {
            size++;
        }
        Node n = new Node(key, value);
        kvMap.put(key, n);
        Node h1 = freqMap.get(1);
        freqMap.put(1, n);
        if (h1 == null) {
            if (tail.next != null) {
                tail.next.next = n;
            }
            n.pre = tail.next;
            tail.next = n;
        } else {
            n.next = h1;
            n.pre = h1.pre;
            if (h1.pre != null) {
                h1.pre.next = n;
            }
            h1.pre = n;
        }
    }
    private void increaseFreq(Node n) {
        int f = n.freq;
        n.freq += 1;

        if (tail.next == n && n.pre != null && n.pre.freq <= n.freq) {
            tail.next = n.pre;
        }

        Node h0 = freqMap.get(f);
        Node h1 = freqMap.get(f + 1);
        freqMap.put(f + 1, n);

        if (h0 == n) {
            // update freqMap because the head is changed
            if (h0.next == null || h0.next.freq != f) {
                freqMap.remove(f);
            } else {
                freqMap.put(f, h0.next);
            }

            if (h1 == null) {
                // n remains the same position, nothing to do
            } else {
                // n.pre must be not null
                // remove from original position
                n.pre.next = n.next;
                if (n.next != null) {
                    n.next.pre = n.pre;
                }
                // insert to target position
                n.next = h1;
                n.pre = h1.pre;
                if (h1.pre != null) {
                    h1.pre.next = n;
                }
                h1.pre = n;
            }
        } else {
            // remove n from its original position
            // n.pre must be not null
            n.pre.next = n.next;
            if (n.next != null) {
                n.next.pre = n.pre;
            }

            if (h1 == null) {
                // make n the previous node of h0
                n.next = h0;
                n.pre = h0.pre;
                if (h0.pre != null) {
                    h0.pre.next = n;
                }
                h0.pre = n;
            } else {
                // make n the previous node of h1
                n.next = h1;
                n.pre = h1.pre;
                if (h1.pre != null) {
                    h1.pre.next = n;
                }
                h1.pre = n;
            }
        }
    }
    private void evict() {
        Node n = tail.next;
        kvMap.remove(n.key);
        tail.next = n.pre;
        if (tail.next != null) {
            tail.next.next = null;
        }
        if (n.pre == null || n.pre.freq != n.freq) {
            freqMap.remove(n.freq);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
