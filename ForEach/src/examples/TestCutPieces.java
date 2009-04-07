package examples;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

import ch.akuhn.util.query.CutPieces;
import ch.akuhn.util.query.Query;

public class TestCutPieces {

    @Test
    public void testCutPieces() {
        Iterable<Integer> elements;
        Iterator<CutPieces<Integer>> query;
        CutPieces<Integer> each;

        elements = Arrays.asList(new Integer[] { 1, 2, 3, 4 });
        query = Query.cutPieces(elements).iterator();
        assertEquals(true, query.hasNext());
        each = query.next();
        assertEquals(1, (int)each.prev);
        assertEquals(2, (int)each.next);
        assertEquals(true, query.hasNext());
        each = query.next();
        assertEquals(2, (int)each.prev);
        assertEquals(3, (int)each.next);
        assertEquals(true, query.hasNext());
        each = query.next();
        assertEquals(3, (int)each.prev);
        assertEquals(4, (int)each.next);
        assertEquals(false, query.hasNext());
    }

}
