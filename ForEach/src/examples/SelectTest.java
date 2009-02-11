package examples;

import java.util.Collection;

import org.junit.Test;
import static org.junit.Assert.*;

import ch.akuhn.util.query.Query;
import ch.akuhn.util.query.Select;

public class SelectTest {    
    
    @Test
    public void testQuery() {
        for (Select<String> each: Query.select(TestQueries.FOX)) {
            each.yield = each.element.length() == 4;
        }
        assertEquals("[over, lazy]", Query.getResult().toString());
    }
    
    @Test
    public void testInstance() {
        Select<String> query = Select.from(TestQueries.FOX);
        for (Select<String> each: query) {
            each.yield = each.element.length() == 4;
        }
        assertEquals("[over, lazy]", query.result().toString());
    }
    
    @Test
    public void testInstanceTwice() {
        
        Select<String> query = Select.from(TestQueries.FOX);
        // first use of query
        for (Select<String> each: query) {
            each.yield = each.element.length() == 4;
        }
        Collection<String> result1 = query.result();
        assertEquals("[over, lazy]", result1.toString());
        assertSame(result1, Query.getResult());
        // second use of query
        for (Select<String> each: query) {
            each.yield = each.element.length() == 3;
        }
        Collection<String> result2 = query.result();
        assertEquals("[The, fox, the, dog]", result2.toString());
        assertSame(result2, Query.getResult());
        assertNotSame(result2, result1);
    }

}
