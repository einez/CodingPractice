package einez.practice.nmock;

import java.util.Map;
import java.util.TreeMap;

public class IntensitySegments {

    private final TreeMap<Integer, SegmentPoint> numToNode;
    private final SegmentPoint dummyHead;

    public IntensitySegments() {
        this.numToNode = new TreeMap<>();
        this.dummyHead = new SegmentPoint();
    }

    public void add(int from, int to, int amount) {
        SegmentPoint preOfFrom = getPreviousSegmentPoint(from);
        SegmentPoint fromPoint = getOrCreateFromPoint(preOfFrom, from);

        SegmentPoint nextPoint = fromPoint.next;
        SegmentPoint previousPoint = fromPoint;
        while (nextPoint != null && nextPoint.value < to) {
            nextPoint.amount += amount;
            previousPoint = nextPoint;
            nextPoint = nextPoint.next;
        }
        if (nextPoint == null || nextPoint.value > to) {
            insertToPoint(to, previousPoint, nextPoint);
        }

        // Adjust amount at the end
        fromPoint.amount += amount;
        compactSegmentPointList(preOfFrom, nextPoint);
    }

    public void set(int from, int to, int amount) {
        SegmentPoint preOfFrom = getPreviousSegmentPoint(from);
        SegmentPoint fromPoint = getOrCreateFromPoint(preOfFrom, from);

        SegmentPoint nextPoint = fromPoint.next;
        while (nextPoint != null && nextPoint.value < to) {
            removeNextPoint(fromPoint);
            nextPoint = fromPoint.next;
        }
        if (nextPoint == null || nextPoint.value > to) {
            insertToPoint(to, fromPoint, nextPoint);
        }

        // Adjust amount at the end
        fromPoint.amount = amount;
        compactSegmentPointList(preOfFrom, nextPoint);
    }

    private SegmentPoint getPreviousSegmentPoint(int value) {
        Map.Entry<Integer, SegmentPoint> lessThanValue = numToNode.lowerEntry(value);
        if (lessThanValue == null) {
            return dummyHead;
        }
        return lessThanValue.getValue();
    }

    private SegmentPoint getOrCreateFromPoint(SegmentPoint preOfFrom, int fromValue) {
        if (preOfFrom.next != null && preOfFrom.next.value == fromValue) {
            return preOfFrom.next;
        }

        SegmentPoint fromPoint = new SegmentPoint(fromValue, preOfFrom.next);
        fromPoint.amount = preOfFrom.amount;
        numToNode.put(fromValue, fromPoint);
        preOfFrom.next = fromPoint;
        return fromPoint;
    }

    private void insertToPoint(int toValue, SegmentPoint prePoint, SegmentPoint nextPoint) {
        SegmentPoint toPoint = new SegmentPoint(toValue, nextPoint);
        toPoint.amount = nextPoint == null ? 0 : prePoint.amount;
        numToNode.put(toValue, toPoint);
        prePoint.next = toPoint;
    }

    private void removeNextPoint(SegmentPoint prePoint) {
        SegmentPoint nextPoint = prePoint.next;
        if (nextPoint == null) {
            return;
        }
        numToNode.remove(nextPoint.value);
        prePoint.next = nextPoint.next;
    }

    private void compactSegmentPointList(SegmentPoint pre, SegmentPoint end) {
        SegmentPoint cur = pre.next;
        while (cur != end) {
            if (pre.amount == 0 && cur.amount == 0) {
                removeNextPoint(pre);
            } else {
                pre = cur;
            }
            cur = pre.next;
        }
        if (cur != null && cur.amount == 0 && pre.amount == 0) {
            removeNextPoint(pre);
        }
    }

    @Override
    public String toString() {
        if (dummyHead.next == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        SegmentPoint n = dummyHead.next;
        while (n != null) {
            sb.append(n).append(",");
            n = n.next;
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append("]");
        return sb.toString();
    }

    static class SegmentPoint {
        int value;
        int amount;
        SegmentPoint next;

        SegmentPoint() {
        }

        SegmentPoint(int value) {
            this.value = value;
        }

        SegmentPoint(int value, SegmentPoint next) {
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

        segments.set(10, 40, 0);
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
