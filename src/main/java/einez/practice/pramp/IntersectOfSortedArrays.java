package einez.practice.pramp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IntersectOfSortedArrays {
    static int[] normalSolution(int[] arr1, int[] arr2) {
        int i = 0;
        int j = 0;
        Set<Integer> set = new HashSet<>();
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] == arr2[j]) {
                set.add(arr1[i]);
                i++;
                j++;
                continue;
            }
            if (arr1[i] < arr2[j]) {
                i++;
            } else {
                j++;
            }
        }
        int index = 0;
        int[] ans = new int[set.size()];
        for (Integer n : set) {
            ans[index] = i;
            index++;
        }
        return ans;
    }

    static int[] findDuplicates(int[] arr1, int[] arr2) {
        int M = arr1.length;
        int N = arr2.length;

        // check m & n
        // 1. to be done


        // 2. binary search
        if (M < N) {
            int[] tmp = arr2;
            arr2 = arr1;
            arr1 = tmp;
        }
        List<Integer> ans = new ArrayList<>();
        int startIndex = 0;
        for (int e : arr2) {
            startIndex = find(arr1, e, startIndex);
            if (startIndex > 0) {
                ans.add(e);
            }
            startIndex = -(startIndex + 1);
            // Arrays.binarySearch()
        }
        int[] result = new int[ans.size()];
        int i = 0;
        for (Integer n : ans) {
            result[i] = n;
            i++;
        }
        return result;
    }

    // binarySearch method
    static int find(int[] arr, int num, int low) {
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == num) {
                return mid;
            }
            if (arr[mid] < num) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -low;
    }
}
