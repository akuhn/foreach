package examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.Collection;

import org.junit.Test;

import ch.akuhn.util.query.Collect2;
import ch.akuhn.util.query.Query;

public class CollectTest {

    @Test
    public void testQuery() {
        for (Collect2<String,Integer> each: Query.collect(TestQueries.FOX, Integer.class)) {
            each.yield = each.element.length();
        }
        assertEquals("[3, 5, 5, 3, 5, 4, 3, 4, 3]", Query.result().toString());
    }
    
    @Test
    public void testInstance() {
        Collect2<String,Integer> query = Collect2.from(TestQueries.FOX, Integer.class);
        for (Collect2<String,Integer> each: query) {
            each.yield = each.element.length();
        }
        assertEquals("[3, 5, 5, 3, 5, 4, 3, 4, 3]", query.result().toString());
    }
    
    @Test
    public void testInstanceTwice() {
        
        Collect2<String,Integer> query = Collect2.from(TestQueries.FOX, Integer.class);
        // first use of query
        for (Collect2<String,Integer> each: query) {
            each.yield = each.element.length();
        }
        Collection<Integer> result1 = query.result();
        assertEquals("[3, 5, 5, 3, 5, 4, 3, 4, 3]", result1.toString());
        assertSame(result1, Query.result());
        // second use of query
        for (Collect2<String,Integer> each: query) {
            each.yield = (int) each.element.charAt(0);
        }
        Collection<Integer> result2 = query.result();
        assertEquals("[84, 113, 98, 102, 106, 111, 116, 108, 100]", result2.toString());
        assertSame(result2, Query.result());
        assertNotSame(result2, result1);
    }
    
}
