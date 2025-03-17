package einez.practice.graph;

import java.util.*;
import java.util.function.Function;

public class Mouse {
    private static final int WALL = 0;
    private static final int PATH = 1;
    private static final int CHEESE = 9;
    private final int[][] maze;
    private final int[] startPosition;

    private int[] currentPosition;
    private static EnumMap<Direction, int[]> moveMap;

    static {
        moveMap = new EnumMap<>(Direction.class);
        moveMap.put(Direction.LEFT, new int[]{-1, 0});
        moveMap.put(Direction.RIGHT, new int[]{1, 0});
        moveMap.put(Direction.UP, new int[]{0, -1});
        moveMap.put(Direction.DOWN, new int[]{0, 1});
    }

    /**
     * Initiate with a maze and a start position
     *
     * @param maze   a 2-dimension array, 0: wall, 1: path, 9: cheese
     * @param startX start row, 0-indexed
     * @param startY start column, 0-indexed
     */
    public Mouse(int[][] maze, int startX, int startY) {
        this.maze = maze;
        this.startPosition = new int[]{startX, startY};
        this.currentPosition = new int[]{startX, startY};
    }

    /**
     * Moves to one of the direction (left, right, up, down) and return false if could not move.
     */
    public boolean move(Direction direction) {
        Optional<int[]> next = getNextValidPosition(currentPosition, direction);
        if (next.isPresent()) {
            currentPosition = next.get();
            return true;
        }
        return false;
    }

    private Optional<int[]> getNextValidPosition(int[] curPos, Direction direction) {
        int[] move = moveMap.get(direction);
        int x = curPos[0] + move[0];
        int y = curPos[1] + move[1];
        if (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == PATH) {
            return Optional.of(new int[]{x, y});
        }
        return Optional.empty();
    }

    /**
     * Returns true if there is a cheese in the current cell.
     */
    public boolean hasCheese() {
        return hasCheese(currentPosition);
    }

    private boolean hasCheese(int[] position) {
        return maze[position[0]][position[1]] == CHEESE;
    }

    /**
     * Should return true and leave the mouse at that location or false if we could not get any cheeseand move the
     * mouse to the start point
     */
    public boolean getCheese() {
        Queue<int[]> bfsQueue = new LinkedList<>();
        bfsQueue.offer(currentPosition);
        Set<Integer> visited = new HashSet<>();
        Function<int[], Integer> transform = pos -> pos[0] * maze[0].length + pos[1];
        visited.add(transform.apply(currentPosition));
        while (!bfsQueue.isEmpty()) {
            int[] head = bfsQueue.poll();
            for (Direction dir : Direction.values()) {
                Optional<int[]> optionalNext = getNextValidPosition(head, dir);
                if (optionalNext.isEmpty()) {
                    continue;
                }

                int[] next = optionalNext.get();
                if (hasCheese(next)) {
                    this.currentPosition = next;
                    return true;
                }

                if (visited.add(transform.apply(next))) {
                    bfsQueue.offer(next);
                }
            }
        }
        this.currentPosition[0] = this.startPosition[0];
        this.currentPosition[1] = this.startPosition[1];
        return false;
    }

    enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}
