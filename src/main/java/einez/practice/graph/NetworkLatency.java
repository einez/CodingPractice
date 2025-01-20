package einez.practice.graph;

import java.util.Comparator;
import java.util.PriorityQueue;

public class NetworkLatency {
    public static void main(String[] args) {
        int g_nodes = 3;
        int g_edges = 3;
        int[] g_from = new int[]{1, 2, 3};
        int[] g_to = new int[]{2, 3, 1};
        int[] g_weight = new int[]{4, 5, 3};
        int k = 2;
        int expected = 3;
        int actual = minLatency(g_nodes, g_edges, g_from, g_to, g_weight, k);
        System.out.printf("actual = %d, assert = %b%n", actual, actual == expected);

        // Test case 1: Small graph
        g_nodes = 4;
        g_edges = 5;
        g_from = new int[]{1, 1, 2, 2, 3};
        g_to = new int[]{2, 3, 3, 4, 4};
        g_weight = new int[]{1, 4, 3, 2, 5};
        k = 2;
        // expected = 3;
        expected = 2;
        actual = minLatency(g_nodes, g_edges, g_from, g_to, g_weight, k);
        System.out.printf("actual = %d, assert = %b%n", actual, actual == expected);

        // Test case 2: Fully connected graph
        g_nodes = 5;
        g_edges = 10;
        g_from = new int[]{1, 1, 1, 1, 2, 2, 2, 3, 3, 4};
        g_to = new int[]{2, 3, 4, 5, 3, 4, 5, 4, 5, 5};
        g_weight = new int[]{2, 2, 2, 2, 3, 3, 3, 4, 4, 5};
        k = 3;
        // expected = 3;
        expected = 2;
        actual = minLatency(g_nodes, g_edges, g_from, g_to, g_weight, k);
        System.out.printf("actual = %d, assert = %b%n", actual, actual == expected);

        // Test case 3: Minimum nodes
        g_nodes = 2;
        g_edges = 1;
        g_from = new int[]{1};
        g_to = new int[]{2};
        g_weight = new int[]{10};
        k = 1;
        expected = 10;
        actual = minLatency(g_nodes, g_edges, g_from, g_to, g_weight, k);
        System.out.printf("actual = %d, assert = %b%n", actual, actual == expected);

        // Test case 4: Edge case with k = g_nodes
        g_nodes = 4;
        g_edges = 6;
        g_from = new int[]{1, 1, 2, 2, 3, 3};
        g_to = new int[]{2, 3, 3, 4, 4, 1};
        g_weight = new int[]{1, 1, 1, 1, 1, 1};
        k = 4;
        expected = 0;
        actual = minLatency(g_nodes, g_edges, g_from, g_to, g_weight, k);
        System.out.printf("actual = %d, assert = %b%n", actual, actual == expected);

    }

    public static int minLatency(int g_nodes, int g_edges, int[] g_from, int[] g_to, int[] g_weight, int k) {
        if (k >= g_nodes) {
            return 0;
        }

        PriorityQueue<Integer> edges = new PriorityQueue<>(g_nodes + 1,
                Comparator.comparingInt(a -> g_weight[a]));
        for (int i = 0; i < g_weight.length; i++) {
            edges.offer(i);
        }

        int subnets = g_nodes;
        int[] union = new int[g_nodes + 1];
        for (int i = 1; i < union.length; i++) {
            union[i] = i;
        }
        int minLat = Integer.MAX_VALUE;
        while (!edges.isEmpty() && subnets > k) {
            int e = edges.poll();
            if (merge(union, g_from[e], g_to[e])) {
                subnets--;
            }
            minLat = g_weight[e];
        }
        return minLat;
    }

    private static boolean merge(int[] union, int n1, int n2) {
        int p1 = find(union,n1);
        int p2 = find(union,n2);
        if (p1 == p2) {
            return false;
        }
        if (p2 < p1) {
            union[p1] = p2;
        } else {
            union[p2] = p1;
        }
        return true;
    }

    private static int find(int[]union, int n) {
        if (union[n] == n) {
            return n;
        }
        int p = union[n];
        union[n] = find(union,p);
        return union[n];
    }
}
