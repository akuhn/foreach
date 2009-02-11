package examples;

import java.util.Collection;

import org.junit.Test;
import static org.junit.Assert.*;

import ch.akuhn.util.query.Query;
import ch.akuhn.util.query.Select;

public class SelectTest {

    @Test
    public void testQuerySelect() {
        for (Select<String> each: Query.select(TestQueries.FOX)) {
            each.yield = each.element.length() == 4;
        }
        assertEquals("[over, lazy]", Query.getResult().toString());
    }
    
    @Test
    public void testSelectFrom() {
        Select<String> selection = Select.from(TestQueries.FOX);
        for (Select<String> each: selection) {
            each.yield = each.element.length() == 4;
        }
        assertEquals("[over, lazy]", selection.result().toString());
    }
    
    @Test
    public void testSelectFromTwice() {
        
        Select<String> selection = Select.from(TestQueries.FOX);
        // first use of query
        for (Select<String> each: selection) {
            each.yield = each.element.length() == 4;
        }
        Collection<String> result1 = selection.result();
        assertEquals("[over, lazy]", result1.toString());
        assertSame(result1, Query.getResult());
        // second use of query
        for (Select<String> each: selection) {
            each.yield = each.element.length() == 3;
        }
        Collection<String> result2 = selection.result();
        assertEquals("[The, fox, the, dog]", result2.toString());
        assertSame(result2, Query.getResult());
        assertNotSame(result2, result1);
    }
    
}
