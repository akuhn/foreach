package examples;

import java.util.Collection;

import org.junit.Test;
import static org.junit.Assert.*;

import ch.akuhn.util.query.Collect;
import ch.akuhn.util.query.Query;

public class CollectTest {

    @Test
    public void testQuery() {
        for (Collect<String,Integer> each: Query.collect(TestQueries.FOX, Integer.class)) {
            each.yield = each.element.length();
        }
        assertEquals("[3, 5, 5, 3, 5, 4, 3, 4, 3]", Query.getResult().toString());
    }
    
    @Test
    public void testInstance() {
        Collect<String,Integer> query = Collect.from(TestQueries.FOX, Integer.class);
        for (Collect<String,Integer> each: query) {
            each.yield = each.element.length();
        }
        assertEquals("[3, 5, 5, 3, 5, 4, 3, 4, 3]", query.result().toString());
    }
    
    @Test
    public void testInstanceTwice() {
        
        Collect<String,Integer> query = Collect.from(TestQueries.FOX, Integer.class);
        // first use of query
        for (Collect<String,Integer> each: query) {
            each.yield = each.element.length();
        }
        Collection<Integer> result1 = query.result();
        assertEquals("[3, 5, 5, 3, 5, 4, 3, 4, 3]", result1.toString());
        assertSame(result1, Query.getResult());
        // second use of query
        for (Collect<String,Integer> each: query) {
            each.yield = (int) each.element.charAt(0);
        }
        Collection<Integer> result2 = query.result();
        assertEquals("[84, 113, 98, 102, 106, 111, 116, 108, 100]", result2.toString());
        assertSame(result2, Query.getResult());
        assertNotSame(result2, result1);
    }
    
}
