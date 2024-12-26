package einez.practice.nmock;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CounterTest {
    @Test
    void exampleSequence() {
        int actualResult;
        Counter counter = mock(Counter.class, withSettings().useConstructor(300));
        doCallRealMethod().when(counter).put(anyString());
        doCallRealMethod().when(counter).get_total_count();
        doCallRealMethod().when(counter).get_count(anyString());

        when(counter.getCurrentTime()).thenReturn(16477118624L);
        counter.put("a");

        when(counter.getCurrentTime()).thenReturn(16477118630L);
        counter.put("a");
        counter.put("b");
        actualResult = counter.get_count("a");
        assertEquals(2, actualResult);

        when(counter.getCurrentTime()).thenReturn(16477118631L);
        actualResult = counter.get_total_count();
        assertEquals(3, actualResult);

        when(counter.getCurrentTime()).thenReturn(16477118925L);
        actualResult = counter.get_count("a");
        assertEquals(1, actualResult);
        actualResult = counter.get_total_count();
        assertEquals(2, actualResult);
    }
}