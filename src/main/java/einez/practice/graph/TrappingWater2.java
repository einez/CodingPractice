package einez.practice.graph;

import einez.practice.ArrayConverter;

import java.util.PriorityQueue;

public class TrappingWater2 {
    public static void main(String[] args) {
        new TrappingWater2().trapRainWater(ArrayConverter.convert2("[[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]"));
    }
    public int trapRainWater(int[][] hm) {
        int m = hm.length;
        if (m < 3) {
            return 0;
        }
        int n = hm[0].length;
        if (n < 3) {
            return 0;
        }

        PriorityQueue<Coordinate> q = new PriorityQueue<>((a, b) -> hm[a.x][a.y] - hm[b.x][b.y]);
        boolean[][] isEdge = new boolean[m][n];
        for (int j = 0; j < n; j++) {
            isEdge[0][j] = true;
            isEdge[m - 1][j] = true;
        }
        for (int i = 1; i < m - 1; i++) {
            isEdge[i][0] = true;
            isEdge[i][n - 1] = true;
        }
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                q.offer(new Coordinate(i, j));
            }
        }
        int res = 0;
        while (!q.isEmpty()) {
            Coordinate p = q.poll();
            res += incrAndUpdate(p, hm, isEdge);
            if (!isEdge[p.x][p.y]) {
                q.offer(p);
            }
        }
        return res;
    }
    static int[][] dt = new int[][]{
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };
    int incrAndUpdate(Coordinate p, int[][] hm, boolean[][] isEdge) {
        int originalH = hm[p.x][p.y];
        int minH = Math.min(Math.min(hm[p.x + 1][p.y], hm[p.x - 1][p.y]), Math.min(hm[p.x][p.y + 1], hm[p.x][p.y - 1]));
        minH = Math.max(originalH, minH);
        for (int[] move: dt) {
            if (hm[p.x + move[0]][p.y + move[1]] <= minH && isEdge[p.x + move[0]][p.y + move[1]]) {
                isEdge[p.x][p.y] = true;
                break;
            }
        }
        hm[p.x][p.y] = minH;
        return minH - originalH;
    }
    static class Coordinate {
        int x;
        int y;
        Coordinate(int a, int b) {
            x = a;
            y = b;
        }
    }
}
