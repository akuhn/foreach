package examples;

import static ch.akuhn.util.Interval.range;
import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

import ch.akuhn.util.Interval;

public class IntervalTest {

    @Test
    public void testRange3() {
        Interval range = range(3);
        Iterator<Integer> it = range.iterator();
        assertEquals(true, it.hasNext());
        assertEquals(0, it.next());
        assertEquals(true, it.hasNext());
        assertEquals(1, it.next());
        assertEquals(true, it.hasNext());
        assertEquals(2, it.next());
        assertEquals(false, it.hasNext());
    }
    
    @Test
    public void testRange1() {
        Interval range = range(1);
        Iterator<Integer> it = range.iterator();
        assertEquals(true, it.hasNext());
        assertEquals(0, it.next());
        assertEquals(false, it.hasNext());
    }

    @Test
    public void testRange0() {
        Interval range = range(0);
        Iterator<Integer> it = range.iterator();
        assertEquals(false, it.hasNext());
    }

    @Test
    public void testRangeNegative() {
        Interval range = range(-23);
        Iterator<Integer> it = range.iterator();
        assertEquals(false, it.hasNext());
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testBeyondRange1() {
        Interval range = range(1);
        Iterator<Integer> it = range.iterator();
        assertEquals(0, it.next());
        it.next();
    }

}
