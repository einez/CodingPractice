package einez.practice;

import java.util.HashSet;
import java.util.Set;

public class Practice {
    enum Direction {
        UP, DOWN, LEFT, RIGHT,
    }

    static abstract class Mouse {

        /**
         * Moves to one of the direction (left, right, up, down) and return false if could not move
         */
        public abstract boolean move(Direction direction);

        /**
         * Returns true if there is a cheese in the current cell.
         */
        public abstract boolean hasCheese();

        /**
         * Should return true and leave the mouse at that location or false if we could not get any cheese and move the
         * mouse to the start point
         */
        public boolean getCheese() {
            if (hasCheese()) {
                return true;
            }
            // current = (0, 0)
            int[] pos = new int[]{0, 0};
            Set<String> visited = new HashSet<>();
            return getCheeseDfs(pos, visited);
        }

        private boolean getCheeseDfs(int[] curPos, Set<String> visited) {
            if (hasCheese()) {
                return true;
            }
            if (!visited.add(getKey(curPos))) {
                return false;
            }
            for (Direction dir : Direction.values()) {
                int[] nextPos = getNextPosition(curPos, dir);
                if (!move(dir)) {
                    continue;
                }
                curPos[0] = nextPos[0];
                curPos[1] = nextPos[1];
                if (getCheeseDfs(curPos, visited)) {
                    return true;
                }
                // move(dir~)
            }
            //
            return false;
        }

        private int[] getNextPosition(int[] curPos, Direction dir) {
            // FIXME
            return new int[0];
        }

        private static String getKey(int[] pos) {
            return pos[0] + ":" + pos[1];
        }
    }

    /*
     *
     * */


    public static void main(String[] args) {

    }
}
