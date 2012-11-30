package ch.akuhn.foreach;

import static java.lang.Math.max;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class Interval implements Iterable<Integer> {

	public static Interval range(int end) {
		return new Interval(0, end, +1);
	}

	public static Interval range(int start, int end) {
		return new Interval(start, end, +1);
	}

	public static Interval range(int start, int end, int step) {
		return new Interval(start, end, step);
	}

	public final int end;

	public final int start;

	public final int step;

	public Interval(final int start, final int end, final int step) {
		if (step == 0 && start != end) throw new IllegalArgumentException();
		this.end = end;
		this.step = step;
		this.start = start;
	}

	// @Override
	public final Iterator<Integer> iterator() {
		return new Iterator<Integer>() {

			private int index = start;

			// @Override
			public final boolean hasNext() {
				return step > 0 ? index < end : index > end;
			}

			// @Override
			public final Integer next() {
				if (!hasNext()) throw new NoSuchElementException();
				int current = index;
				index += step;
				return current;
			}

			// @Override
			public final void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	public int size() {
		return step == 0 ? 0 : max(0, (end - start + step + (step < 0 ? +1 : -1)) / step);
	}

	public int[] asArray() {
		int[] array = new int[size()];
		Iterator<Integer> it = iterator();
		for (int n = 0; n < array.length; n++)
			array[n] = it.next();
		assert !it.hasNext();
		return array;
	}

	public static class Tests {

		@Test
		public void testRange3() {
			Interval range = range(3);
			Iterator<Integer> it = range.iterator();
			assertEquals(true, it.hasNext());
			assertEquals(0, (int) it.next());
			assertEquals(true, it.hasNext());
			assertEquals(1, (int) it.next());
			assertEquals(true, it.hasNext());
			assertEquals(2, (int) it.next());
			assertEquals(false, it.hasNext());
		}

		@Test
		public void testRange1() {
			Interval range = range(1);
			Iterator<Integer> it = range.iterator();
			assertEquals(true, it.hasNext());
			assertEquals(0, (int) it.next());
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

		@Test(expected = NoSuchElementException.class)
		public void testBeyondRange1() {
			Interval range = range(1);
			Iterator<Integer> it = range.iterator();
			assertEquals(0, (int) it.next());
			it.next();
		}

		@Test
		public void testSize() {
			assertEquals(0, range(0, -3, 3).size());
			assertEquals(0, range(0, -2, 3).size());
			assertEquals(0, range(0, -1, 3).size());
			assertEquals(0, range(0, 0, 3).size());
			assertEquals(1, range(0, 1, 3).size());
			assertEquals(1, range(0, 2, 3).size());
			assertEquals(1, range(0, 3, 3).size());
			assertEquals(2, range(0, 4, 3).size());
			assertEquals(2, range(0, 5, 3).size());
			assertEquals(2, range(0, 6, 3).size());
			assertEquals(3, range(0, 7, 3).size());
		}

		@Test
		public void testAsArray() {
			assertEquals("[]", Arrays.toString(range(0, -3, 3).asArray()));
			assertEquals("[]", Arrays.toString(range(0, -2, 3).asArray()));
			assertEquals("[]", Arrays.toString(range(0, -1, 3).asArray()));
			assertEquals("[]", Arrays.toString(range(0, 0, 3).asArray()));
			assertEquals("[0]", Arrays.toString(range(0, 1, 3).asArray()));
			assertEquals("[0]", Arrays.toString(range(0, 2, 3).asArray()));
			assertEquals("[0]", Arrays.toString(range(0, 3, 3).asArray()));
			assertEquals("[0, 3]", Arrays.toString(range(0, 4, 3).asArray()));
			assertEquals("[0, 3]", Arrays.toString(range(0, 5, 3).asArray()));
			assertEquals("[0, 3]", Arrays.toString(range(0, 6, 3).asArray()));
			assertEquals("[0, 3, 6]", Arrays.toString(range(0, 7, 3).asArray()));
		}

		@Test
		public void testAsArray2() {
			assertEquals("[]", Arrays.toString(range(0, 3, -3).asArray()));
			assertEquals("[]", Arrays.toString(range(0, 2, -3).asArray()));
			assertEquals("[]", Arrays.toString(range(0, 1, -3).asArray()));
			assertEquals("[]", Arrays.toString(range(0, 0, -3).asArray()));
			assertEquals("[0]", Arrays.toString(range(0, -1, -3).asArray()));
			assertEquals("[0]", Arrays.toString(range(0, -2, -3).asArray()));
			assertEquals("[0]", Arrays.toString(range(0, -3, -3).asArray()));
			assertEquals("[0, -3]", Arrays.toString(range(0, -4, -3).asArray()));
			assertEquals("[0, -3]", Arrays.toString(range(0, -5, -3).asArray()));
			assertEquals("[0, -3]", Arrays.toString(range(0, -6, -3).asArray()));
			assertEquals("[0, -3, -6]", Arrays.toString(range(0, -7, -3).asArray()));
		}

		@Test
		public void testSize2() {
			assertEquals(0, range(0, 3, -3).size());
			assertEquals(0, range(0, 2, -3).size());
			assertEquals(0, range(0, 1, -3).size());
			assertEquals(0, range(0, 0, -3).size());
			assertEquals(1, range(0, -1, -3).size());
			assertEquals(1, range(0, -2, -3).size());
			assertEquals(1, range(0, -3, -3).size());
			assertEquals(2, range(0, -4, -3).size());
			assertEquals(2, range(0, -5, -3).size());
			assertEquals(2, range(0, -6, -3).size());
			assertEquals(3, range(0, -7, -3).size());
		}

	}

}
