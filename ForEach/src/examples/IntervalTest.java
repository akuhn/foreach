package examples;

import static ch.akuhn.util.Interval.range;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
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
        assertEquals(0, (int)it.next());
        assertEquals(true, it.hasNext());
        assertEquals(1, (int)it.next());
        assertEquals(true, it.hasNext());
        assertEquals(2, (int)it.next());
        assertEquals(false, it.hasNext());
    }
    
    @Test
    public void testRange1() {
        Interval range = range(1);
        Iterator<Integer> it = range.iterator();
        assertEquals(true, it.hasNext());
        assertEquals(0, (int)it.next());
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
        assertEquals(0, (int)it.next());
        it.next();
    }
    
    @Test
    public void testSize() {
        assertEquals(0, range(0,-3,3).size());
        assertEquals(0, range(0,-2,3).size());
        assertEquals(0, range(0,-1,3).size());
        assertEquals(0, range(0,0,3).size());
        assertEquals(1, range(0,1,3).size());
        assertEquals(1, range(0,2,3).size());
        assertEquals(1, range(0,3,3).size());
        assertEquals(2, range(0,4,3).size());
        assertEquals(2, range(0,5,3).size());
        assertEquals(2, range(0,6,3).size());
        assertEquals(3, range(0,7,3).size());
    }

    @Test
    public void testAsArray() {
        assertEquals("[]", Arrays.toString(range(0,-3,3).asArray()));
        assertEquals("[]", Arrays.toString(range(0,-2,3).asArray()));
        assertEquals("[]", Arrays.toString(range(0,-1,3).asArray()));
        assertEquals("[]", Arrays.toString(range(0,0,3).asArray()));
        assertEquals("[0]", Arrays.toString(range(0,1,3).asArray()));
        assertEquals("[0]", Arrays.toString(range(0,2,3).asArray()));
        assertEquals("[0]", Arrays.toString(range(0,3,3).asArray()));
        assertEquals("[0, 3]", Arrays.toString(range(0,4,3).asArray()));
        assertEquals("[0, 3]", Arrays.toString(range(0,5,3).asArray()));
        assertEquals("[0, 3]", Arrays.toString(range(0,6,3).asArray()));
        assertEquals("[0, 3, 6]", Arrays.toString(range(0,7,3).asArray()));
    }
    
    @Test
    public void testAsArray2() {
        assertEquals("[]", Arrays.toString(range(0,3,-3).asArray()));
        assertEquals("[]", Arrays.toString(range(0,2,-3).asArray()));
        assertEquals("[]", Arrays.toString(range(0,1,-3).asArray()));
        assertEquals("[]", Arrays.toString(range(0,0,-3).asArray()));
        assertEquals("[0]", Arrays.toString(range(0,-1,-3).asArray()));
        assertEquals("[0]", Arrays.toString(range(0,-2,-3).asArray()));
        assertEquals("[0]", Arrays.toString(range(0,-3,-3).asArray()));
        assertEquals("[0, -3]", Arrays.toString(range(0,-4,-3).asArray()));
        assertEquals("[0, -3]", Arrays.toString(range(0,-5,-3).asArray()));
        assertEquals("[0, -3]", Arrays.toString(range(0,-6,-3).asArray()));
        assertEquals("[0, -3, -6]", Arrays.toString(range(0,-7,-3).asArray()));
    }
    
    
    @Test
    public void testSize2() {
        assertEquals(0, range(0,3,-3).size());
        assertEquals(0, range(0,2,-3).size());
        assertEquals(0, range(0,1,-3).size());
        assertEquals(0, range(0,0,-3).size());
        assertEquals(1, range(0,-1,-3).size());
        assertEquals(1, range(0,-2,-3).size());
        assertEquals(1, range(0,-3,-3).size());
        assertEquals(2, range(0,-4,-3).size());
        assertEquals(2, range(0,-5,-3).size());
        assertEquals(2, range(0,-6,-3).size());
        assertEquals(3, range(0,-7,-3).size());
    }
    
}
