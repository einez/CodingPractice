package einez.practice.spi;

import java.util.ServiceLoader;

public class TestSearch {
    public static void main(String[] args) {
        ServiceLoader<Search> s = ServiceLoader.load(Search.class);
        for (Search search : s) {
            search.searchDoc("hello world");
        }
    }
}
