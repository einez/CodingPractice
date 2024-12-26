package einez.practice.pramp;

import einez.practice.ArrayConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessTimeInMallTest {

    @Test
    void findBusiestPeriod() {
        String[] dataStrings = new String[]{
                "[[1487799426, 21, 1]]",
                "[[1487799425, 21, 0], [1487799427, 22, 1], [1487901318, 7, 0]]",
                "[[1487799425, 21, 1], [1487799425, 4, 0], [1487901318, 7, 0]]",
                "[[1487799425, 14, 1], [1487799425, 4, 0], [1487799425, 2, 0], [1487800378, 10, 1], [1487801478, 18, 0], [1487801478, 18, 1], [1487901013, 1, 0], [1487901211, 7, 1], [1487901211, 7, 0]]",
                "[[1487799425, 14, 1], [1487799425, 4, 1], [1487799425, 2, 1], [1487800378, 10, 1], [1487801478, 18, 1], [1487901013, 1, 1], [1487901211, 7, 1], [1487901211, 7, 1]]",
                "[[1487799425, 14, 1], [1487799425, 4, 0], [1487799425, 2, 0], [1487800378, 10, 1], [1487801478, 18, 0], [1487801478, 19, 1], [1487801478, 1, 0], [1487801478, 1, 1], [1487901013, 1, 0], [1487901211, 7, 1], [1487901211, 8, 0]]"
        };
        int[] expectedResults = new int[]{
                1487799426,
                1487799427,
                1487799425,
                1487800378,
                1487901211,
                1487801478
        };
        for (int i = 0; i < dataStrings.length; i++) {
            int[][] data = ArrayConverter.convert2(dataStrings[i]);
            int expected = expectedResults[i];
            int actual = BusinessTimeInMall.findBusiestPeriod(data);
            assertEquals(expected, actual);
        }
    }
}