package examples;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import static ch.akuhn.util.query.Query.$result;
import static ch.akuhn.util.query.Query.groupedBy;
import static org.junit.Assert.*;

import ch.akuhn.util.query.GroupedBy;
import ch.akuhn.util.query.Query;
import ch.akuhn.util.query.Select;

public class GroupedByTest {    
    
    @Test
    public void testQuery() {
        for (GroupedBy<String> each : groupedBy(TestQueries.FOX)) {
            each.yield = each.element.length();
        }
        assertTrue(Query.result().toString().contains("3=[The, fox, the, dog]"));
        assertTrue(Query.result().toString().contains("4=[over, lazy]"));
        assertTrue(Query.result().toString().contains("5=[quick, brown, jumps]"));
    }
    
    @Test
    public void testInstance() {
        GroupedBy<String> query = GroupedBy.from(TestQueries.FOX);
        for (GroupedBy<String> each: query) {
            each.yield = each.element.length();
        }
        assertTrue(query.result().toString().contains("3=[The, fox, the, dog]"));
        assertTrue(query.result().toString().contains("4=[over, lazy]"));
        assertTrue(query.result().toString().contains("5=[quick, brown, jumps]"));
    }
    
    @Test
    public void testInstanceTwice() {
        
        GroupedBy<String> query = GroupedBy.from(TestQueries.FOX);
        // first use of query
        for (GroupedBy<String> each: query) {
            each.yield = each.element.length();
        }
        Map<Object,Collection<String>> result1 = query.result();
        assertTrue(result1.toString().contains("3=[The, fox, the, dog]"));
        assertTrue(result1.toString().contains("4=[over, lazy]"));
        assertTrue(result1.toString().contains("5=[quick, brown, jumps]"));
        assertSame(result1, Query.result());
        // second use of query
        for (GroupedBy<String> each: query) {
            each.yield = each.element.length() > 4;
        }
        Map<Object,Collection<String>> result2 = query.result();
        System.out.println(result2);
        assertTrue(result2.toString().contains("true=[quick, brown, jumps]"));
        assertTrue(result2.toString().contains("false=[The, fox, over, the, lazy, dog]"));
        assertSame(result2, Query.result());
        assertNotSame(result2, result1);
    }

}
