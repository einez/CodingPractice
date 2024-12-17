package einez.practice.miscellaneous;

import einez.practice.ArrayConverter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

class LFUCacheTest {
    @Test
    void lfuLarge() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, URISyntaxException {
        call(
                loadActions("actions.txt"),
                loadParams("params.txt")
        );
    }

    @Test
    void lfu() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        call(action0(), param0());
        call(action1(), param1());
        call(action2(), param2());
    }

    void call(String[] act, int[][] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LFUCache cache = new LFUCache(args[0][0]);
        for (int i = 1; i < act.length; i++) {
            if (args[i].length == 1) {
                cache.getClass().getDeclaredMethod(act[i], int.class).invoke(cache, args[i][0]);
            } else {
                cache.getClass().getDeclaredMethod(act[i], int.class, int.class).invoke(cache, args[i][0], args[i][1]);
            }
        }
    }

    // @Test
    void testFileLoader() throws IOException, URISyntaxException {
        // Load actions and params
        String[] actions = loadActions("actions.txt");
        int[][] params = loadParams("params.txt");

        // Print the loaded data for testing
        System.out.println("Actions: " + Arrays.toString(actions));
        System.out.println("Params: " + Arrays.deepToString(params));

    }

    public static String[] loadActions(String fileName) throws IOException, URISyntaxException {
        URL res = LFUCacheTest.class.getClassLoader().getResource(fileName);
        // Read the entire file content into a string
        String content = Files.readString(Paths.get(res.toURI())).trim();
        // Remove the brackets and split the content by commas, then trim quotes
        content = content.substring(1, content.length() - 1); // Remove surrounding brackets
        String[] actions = content.split(",");
        for (int i = 0; i < actions.length; i++) {
            actions[i] = actions[i].trim().replaceAll("\"", ""); // Remove quotes and extra spaces
        }
        return actions;
    }

    public static int[][] loadParams(String fileName) throws IOException, URISyntaxException {
        URL res = LFUCacheTest.class.getClassLoader().getResource(fileName);
        // Read the entire file content into a string
        String content = Files.readString(Paths.get(res.toURI())).trim();
        // Remove the outer brackets
        content = content.substring(1, content.length() - 1);
        // Split the content into individual arrays
        String[] rows = content.split("\\],\\[");
        int[][] params = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            // Remove remaining brackets and spaces
            String row = rows[i].replace("[", "").replace("]", "").trim();
            if (!row.isEmpty()) {
                // Split row by commas and convert to integers
                String[] values = row.split(",");
                params[i] = Arrays.stream(values).mapToInt(Integer::parseInt).toArray();
            } else {
                // Handle empty rows if any
                params[i] = new int[0];
            }
        }
        return params;
    }

    static String[] action0() {
        return new String[]{"LFUCache", "put", "put", "get", "get", "put", "get", "get", "get"};
    }

    static int[][] param0() {
        return ArrayConverter.convert2(
                "[[2],[2,1],[3,2],[3],[2],[4,3],[2],[3],[4]]"
        );
    }

    static String[] action1() {
        return new String[]{"LFUCache", "put", "put", "get", "get", "get", "put", "put", "get", "get", "get", "get"};
    }

    static int[][] param1() {
        return ArrayConverter.convert2(
                "[[3],[2,2],[1,1],[2],[1],[2],[3,3],[4,4],[3],[2],[1],[4]]"
        );
    }

    static String[] action2() {
        return new String[]{"LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"};
    }

    static int[][] param2() {
        return ArrayConverter.convert2(
                "[[2],[1,1],[2,2],[1],[3,3],[2],[3],[4,4],[1],[3],[4]]"
        );
    }
}