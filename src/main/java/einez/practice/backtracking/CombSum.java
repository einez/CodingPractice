package einez.practice.backtracking;

import java.util.ArrayList;
import java.util.List;

public class CombSum {
    public static void main(String[] args) {
        new CombSum().combinationSum(new int[]{2, 3, 5}, 8);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        comb(ans, new ArrayList<>(), candidates, 0, target);
        return ans;
    }

    private void comb(List<List<Integer>> ans, List<Integer> list, int[] cand, int start, int target) {
        if (target == 0) {
            ans.add(new ArrayList<>(list));
            return;
        }
        if (start >= cand.length) {
            return;
        }
        int f = cand[start];
        int power = 0;
        while (power <= target / f) {
            comb(ans, list, cand, start + 1, target - power * f);
            list.add(f);
            power++;
        }
        while (power > 0) {
            list.remove(list.size() - 1);
            power--;
        }
    }
}
