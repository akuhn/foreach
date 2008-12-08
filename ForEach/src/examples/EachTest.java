package examples;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ch.akuhn.util.Each;

public class EachTest {

    private Collection<String> words;
    
    @Before
    public void setup() {
        words = new LinkedList<String>();
        words.add("Lorem");
        words.add("ipsum");
        words.add("dolor");
    };
    
    @Test
    public void testLorem() {
        Iterator<Each<String>> it = Each.withIndex(words).iterator();
        Each<String> each;
        assertEquals(true, it.hasNext());
        each = it.next();
        assertEquals("Lorem", each.element);
        assertEquals(0, each.index);
        assertEquals(true, it.hasNext());
        each = it.next();
        assertEquals("ipsum", each.element);
        assertEquals(1, each.index);
        assertEquals(true, it.hasNext());
        each = it.next();
        assertEquals("dolor", each.element);
        assertEquals(2, each.index);
        assertEquals(false, it.hasNext());
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testBeyondLorem() {
        Iterator<Each<String>> it = Each.withIndex(words).iterator();
        it.next();
        it.next();
        it.next();
        assertEquals(false, it.hasNext());
        it.next();
    }
    
}
