package einez.practice.nmock;

import java.util.Map;
import java.util.TreeMap;

public class IntensitySegmentsV1 {
    private final TreeMap<Integer, Node> numToNode;
    private final Node dummyHead;

    public IntensitySegmentsV1() {
        this.numToNode = new TreeMap<>();
        this.dummyHead = new Node();
    }

    public void add(int from, int to, int amount) {
        // find the previous node that is strictly less than "from"
        Map.Entry<Integer, Node> lt = numToNode.lowerEntry(from);
        Node preNode = lt == null ? dummyHead : lt.getValue();

        Node fromNode;
        if (preNode.next == null || preNode.next.value != from) {
            fromNode = new Node(from, preNode.next);
            fromNode.amount = preNode.amount + amount;
            preNode.next = fromNode;
            numToNode.put(from, fromNode);
        } else {
            fromNode = preNode.next;
            fromNode.amount += amount;
        }

        addAmountUntil(preNode, fromNode, amount, to);
    }

    /**
     * Iterate the node list, until the last node is:
     * 1. null
     * 2. greater or equal to the given "to" value, i.e. the parameter "untilValue"
     *
     * @param ancestor   parent of parent
     * @param parent     parent node
     * @param amount     amount to add
     * @param untilValue the original "to" value
     */
    private void addAmountUntil(Node ancestor, Node parent, int amount, int untilValue) {
        // adjust list: remove empty segments
        if (ancestor.amount == 0 && parent.amount == 0) {
            ancestor.next = parent.next;
            numToNode.remove(parent.value);
            parent = ancestor;
        }

        Node cur = parent.next;
        if (cur == null || cur.value > untilValue) {
            Node toNode = new Node(untilValue, cur);
            parent.next = toNode;
            toNode.amount = cur == null ? 0 : cur.amount + amount;
            numToNode.put(untilValue, toNode);
            return;
        }

        if (cur.value == untilValue) {
            // adjust list
            if (cur.amount == 0 && parent.amount == 0) {
                parent.next = cur.next;
            }
            return;
        }

        cur.amount += amount;
        addAmountUntil(parent, cur, amount, untilValue);

    }

    public void set(int from, int to, int amount) {
        // find the previous node that is strictly less than "from"
        Map.Entry<Integer, Node> lt = numToNode.lowerEntry(from);
        Node preNode = lt == null ? dummyHead : lt.getValue();

        Node fromNode;
        int preAmount;
        if (preNode.next == null || preNode.next.value != from) {
            fromNode = new Node(from, preNode.next);
            preNode.next = fromNode;
            numToNode.put(from, fromNode);
            preAmount = preNode.amount;
        } else {
            fromNode = preNode.next;
            preAmount = fromNode.amount;
        }
        fromNode.amount = amount;

        Node next = fromNode.next;
        while (next != null) {
            if (next.value < to) {
                preAmount = next.amount;
                numToNode.remove(next.value);
                fromNode.next = next.next;
                next = fromNode.next;
                continue;
            }
            if (next.value == to) {
                break;
            }
            fromNode.next = new Node(to, next);
            fromNode.next.amount = preAmount;
            numToNode.put(to, fromNode.next);
            break;
        }
    }

    @Override
    public String toString() {
        if (dummyHead.next == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        Node n = dummyHead.next;
        while (n != null) {
            sb.append(n).append(", ");
            n = n.next;
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
        return sb.toString();
    }

    static class Node {
        int value;
        int amount;
        Node next;

        Node() {
        }

        Node(int value) {
            this.value = value;
        }

        Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return String.format("[%d, %d]", this.value, this.amount);
        }
    }

    // Please uncomment to run the given test cases
    /*public static void main(String[] args) {
        IntensitySegments segments = new IntensitySegments();
        System.out.println(segments);
        //  []

        segments.add(10, 30, 1);
        System.out.println(segments);
        //  [[10, 1], [30, 0]]

        segments.add(20, 40, 1);
        System.out.println(segments);
        //  [[10, 1], [20, 2], [30, 1], [40, 0]]

        segments.add(10, 40, -2);
        System.out.println(segments);
        //  [[10, -1], [20, 0], [30, -1], [40, 0]]

        // ---second test case---
        segments = new IntensitySegments();
        System.out.println(segments);
        //  []

        segments.add(10, 30, 1);
        System.out.println(segments);
        //  [[10, 1], [30, 0]]

        segments.add(20, 40, 1);
        System.out.println(segments);
        //  [[10, 1], [20, 2], [30, 1], [40, 0]]

        segments.add(10, 40, -1);
        System.out.println(segments);
        //  [[20, 1], [30, 0]]

        segments.add(10, 40, -1);
        System.out.println(segments);
        //  [[10, -1], [20, 0], [30, -1], [40, 0]]
    }*/

    // Please uncomment to run the test cases for both "add" and "set"
    /*public static void main(String[] args) {
        IntensitySegments segments = new IntensitySegments();
        System.out.println(segments);
        //  []

        segments.add(10, 30, 1);
        System.out.println(segments);
        //  [[10, 1], [30, 0]]

        segments.add(20, 40, 1);
        System.out.println(segments);
        //  [[10, 1], [20, 2], [30, 1], [40, 0]]

        segments.set(15, 35, -1);
        System.out.println(segments);
        //  [[10, 1], [15, -1], [35, 1], [40, 0]]

        segments.set(10, 40, -1);
        System.out.println(segments);
        //  [[10, -1], [40, 0]]
    }*/
}
