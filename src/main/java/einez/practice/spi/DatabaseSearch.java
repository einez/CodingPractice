package einez.practice.spi;

import java.util.List;

public class DatabaseSearch implements Search {
    @Override
    public List<String> searchDoc(String keyword) {
        System.out.println("Search from database: " + keyword);
        return null;
    }
}