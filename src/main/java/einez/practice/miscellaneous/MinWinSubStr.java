package einez.practice.miscellaneous;

public class MinWinSubStr {
    public static void main(String[] args) {

        new MinWinSubStr().minWindow("ADOBECODEBANC", "ABC");
    }

    public String minWindow(String s, String t) {
        int[] tmap = new int[128];
        int[] win = new int[128];
        for (char c : t.toCharArray()) {
            tmap[c] += 1;
        }

        char[] chars = s.toCharArray();
        int n = chars.length;
        String ans = "";
        int minLen = n + 1;

        for (int start = 0, i = 0; i < n; i++) {
            char c = chars[i];
            if (tmap[c] == 0) {
                continue;
            }

            if (i - start + 1 >= minLen) {
                updateWindow(chars, win, start, i - minLen + 2);
                start = i - minLen + 2;
            }
            while (start < n && tmap[chars[start]] == 0) {
                start++;
            }

            win[c] += 1;
            if (win[c] != tmap[c] || !qualified(win, tmap)) {
                continue;
            }
            while (win[chars[start]] > tmap[chars[start]]) {
                start = shrink(chars, win, tmap, start);
            }
            minLen = i - start + 1;
            ans = new String(chars, start, minLen);
            start = shrink(chars, win, tmap, start);
        }
        return ans;
    }

    private int shrink(char[] chars, int[] win, int[] map, int start) {
        win[chars[start]] -= 1;
        start++;
        while (start < chars.length && map[chars[start]] == 0) {
            start++;
        }
        return start;
    }

    private boolean qualified(int[] win, int[] tmap) {
        for (int i = 0; i < win.length; i++) {
            if (win[i] < tmap[i]) {
                return false;
            }
        }
        return true;
    }

    private void updateWindow(char[] chars, int[] win, int start, int to) {
        while (start < chars.length && start < to) {
            if (win[chars[start]] > 0) {
                win[chars[start]] -= 1;
            }
            start++;
        }
    }
}
