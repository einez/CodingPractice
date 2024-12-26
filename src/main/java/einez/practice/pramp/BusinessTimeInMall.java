package einez.practice.pramp;

public class BusinessTimeInMall {
    static int findBusiestPeriod(int[][] data) {
        int curPeople = 0;
        int maxPeople = Integer.MIN_VALUE;
        int peekTime = data[0][0];
        for (int i = 0; i < data.length; i++) {
            int net = data[i][2] == 0 ? -data[i][1] : data[i][1];
            curPeople += net;
            if (i < data.length - 1 && data[i][0] == data[i + 1][0]) {
                continue;
            }
            if (maxPeople < curPeople) {
                maxPeople = curPeople;
                peekTime = data[i][0];
            }
        }
        return peekTime;
    }
}
