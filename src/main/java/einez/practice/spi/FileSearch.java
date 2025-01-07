package einez.practice.spi;

import java.util.List;

public class FileSearch implements Search {
    @Override
    public List<String> searchDoc(String keyword) {
        System.out.println("Search from file: " + keyword);
        return null;
    }
}
