package examples;

import org.junit.Test;
import static org.junit.Assert.*;

import ch.akuhn.util.query.Detect;
import ch.akuhn.util.query.Query;

public class DetectTest {    
    
    @Test
    public void testQuery() {
        for (Detect<String> each: Query.detect(TestQueries.FOX)) {
            each.yield = each.element.length() == 4;
        }
        assertEquals("over", Query.getResult().toString());
    }
    
    @Test
    public void testInstance() {
        Detect<String> query = Detect.from(TestQueries.FOX);
        for (Detect<String> each: query) {
            each.yield = each.element.length() == 4;
        }
        assertEquals("over", query.result().toString());
    }
    
    @Test
    public void testInstanceTwice() {
        Detect<String> query = Detect.from(TestQueries.FOX);
        // first use of query
        for (Detect<String> each: query) {
            each.yield = each.element.length() == 4;
        }
        String result1 = query.result();
        assertEquals("over", result1.toString());
        assertSame(result1, Query.getResult());
        // second use of query
        for (Detect<String> each: query) {
            each.yield = each.element.length() == 5;
        }
        String result2 = query.result();
        assertEquals("quick", result2.toString());
        assertSame(result2, Query.getResult());
        assertNotSame(result2, result1);
    }

    @Test
    public void testDetectIfNone() {
        Detect<String> query = Detect.from(TestQueries.FOX);
        for (Detect<String> each: query) {
            each.yield = each.element.length() == 2;
        }
        assertEquals(null, query.result());
        String string = "default";
        assertSame(string, query.resultIfNone(string).toString());
    }
    
}
