package einez.practice.pramp;

import java.util.*;

public class SplitNArryTree {

    static class Node {
        int val;
        List<Node> children;

        Node(int val) {
            this.val = val;
            this.children = new ArrayList<>();
        }
    }

    static class Solution {
        boolean splitToTwoParts(Node root) {
            if (root == null) {
                return true;
            }
            Map<Node, Integer> numOfSubTree = new HashMap<>();
            traverse(root, numOfSubTree);

            List<Node> halfTrees = new ArrayList<>();
            int totalNum = numOfSubTree.get(root);
            if (totalNum % 2 == 1) {
                return false;
            }
            for (Map.Entry<Node, Integer> entry : numOfSubTree.entrySet()) {
                if (entry.getValue() == (totalNum / 2) && entry.getKey().val == root.val) {
                    halfTrees.add(entry.getKey());
                }
            }


            for (Node candidate : halfTrees) {
                if (equalToNode(root, candidate)) {
                    return true;
                }
            }
            return false;
        }

        void traverse(Node node, Map<Node, Integer> numOfSubTree) {
            if (node.children.isEmpty()) {
                numOfSubTree.put(node, 1);
                return;
            }
            int count = 0;
            for (Node child : node.children) {
                traverse(child, numOfSubTree);
                count += numOfSubTree.get(child);
            }
            numOfSubTree.put(node, count);
        }

        boolean equalToNode(Node root, Node candidate) {
            Queue<Node> qr = new LinkedList<>();
            qr.offer(root);
            Queue<Node> cr = new LinkedList();
            cr.offer(candidate);
            while (!qr.isEmpty() && !cr.isEmpty()) {
                Node c1 = qr.poll();
                Node c2 = cr.poll();
                if (c1.val != c2.val) {
                    return false;
                }
                int counter1 = 0;
                for (Node n : c1.children) {
                    if (n != candidate) {
                        counter1++;
                        qr.offer(n);
                    }
                }
                if (counter1 != c2.children.size()) {
                    return false;
                }
                for (Node n : c2.children) {
                    cr.offer(n);
                }
            }
            return qr.isEmpty() && cr.isEmpty();
        }

    }
}
