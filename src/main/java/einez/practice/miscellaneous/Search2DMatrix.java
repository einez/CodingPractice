package einez.practice.miscellaneous;

import einez.practice.ArrayConverter;

import java.util.Arrays;

public class Search2DMatrix {
    public static void main(String[] args) {
//        int[][] matrix = ArrayConverter.convert2("[[1,3,5,7],[10,11,16,20],[23,30,34,60]]");
//        new Search2DMatrix().searchMatrix(matrix, 13);
        int[][] matrix = ArrayConverter.convert2("[[1,3,5,7],[10,11,16,20],[23,30,34,60]]");
        new Search2DMatrix().searchMatrix(matrix, 3);
    }
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        if (target < matrix[0][0] || target > matrix[m - 1][n - 1]) {
            return false;
        }

        int row = bsrow(matrix, target);
        if (matrix[row][0] == target) {
            return true;
        }

        return bsearch(matrix[row], target);
    }
    private boolean bsearch(int[] arr, int target) {
        return Arrays.binarySearch(arr, target) >= 0;
    }
    private int bsrow(int[][] matrix, int target) {
        int low = 0;
        int high = matrix.length - 1;
        if (matrix[low][0] == target) {
            return low;
        }
        if (matrix[high][0] == target) {
            return high;
        }
        int mid = low + (high - low) / 2;
        while (low < high) {
            mid = low + (high - low) / 2;
            if (matrix[mid][0] == target) {
                return mid;
            }
            if (matrix[mid][0] < target) {
                low = mid + 1;
                if (matrix[low][0] == target) {
                    return low;
                }
                if (matrix[low][0] > target) {
                    return mid;
                }
            } else {
                high = mid - 1;
                if (high < 0) {
                    return mid;
                }
                if (matrix[high][0] == target) {
                    return high;
                }
            }
            mid = low + (high - low) / 2;
        }
        return mid;
    }
}
