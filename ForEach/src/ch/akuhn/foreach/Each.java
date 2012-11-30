package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public abstract class Each {

	public static <T> Iterable<List<T>> cons(int length, T[] all) {
		return Each.cons(length, Arrays.asList(all));
	}

	public static <T> Iterable<List<T>> cons(final int length, final Iterable<T> all) {
		return new Iterable<List<T>>() {
			@Override
			public Iterator<List<T>> iterator() {
				return new Iterator<List<T>>() {

					private Iterator<T> it = all.iterator();
					private Object[] array = initialize();

					private Object[] initialize() {
						Object[] array = new Object[length];
						for (int n = 1; n < array.length; n++) {
							if (!it.hasNext()) return null;
							array[n] = it.next();
						}
						return array;
					}

					@Override
					public boolean hasNext() {
						return array != null && it.hasNext();
					}

					@Override
					public List<T> next() {
						if (!hasNext()) throw new NoSuchElementException();
						Object[] copy = new Object[array.length];
						System.arraycopy(array, 1, copy, 0, copy.length - 1);
						copy[copy.length - 1] = it.next();
						return (List<T>) Arrays.asList(array = copy);
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}

				};
			}
		};
	}

	public static <T> Iterable<List<T>> slice(int length, T[] all) {
		return Each.slice(length, Arrays.asList(all));
	}

	public static <T> Iterable<List<T>> slice(final int length, final Iterable<T> all) {
		return new Iterable<List<T>>() {
			@Override
			public Iterator<List<T>> iterator() {
				return new Iterator<List<T>>() {

					private Iterator<T> it = all.iterator();
					private Object[] array = null;

					private Object[] fetch() {
						Object[] array = new Object[length];
						for (int n = 0; n < array.length; n++) {
							if (!it.hasNext()) return null;
							array[n] = it.next();
						}
						return array;
					}

					@Override
					public boolean hasNext() {
						if (array == null && it.hasNext()) array = fetch();
						return array != null;
					}

					@Override
					public List<T> next() {
						if (!hasNext()) throw new NoSuchElementException();
						List<T> slice = (List<T>) Arrays.asList(array);
						array = null;
						return slice;
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}

				};
			}
		};
	}

	public static class Examples {

		@Test
		public void shouldUseSlidingWindow() {
			String[] words = "A B C D E".split(" ");
			StringBuffer buf = new StringBuffer();

			for (List<String> each: Each.cons(3, words)) {
				buf.append(each);
			}

			assertEquals("[A, B, C][B, C, D][C, D, E]", buf.toString());
		}

		@Test
		public void shouldNotIterateWhenTooShort() {
			String[] words = "A B".split(" ");
			StringBuffer buf = new StringBuffer();

			for (List<String> each: Each.cons(3, words)) {
				buf.append(each);
			}

			assertEquals("", buf.toString());
		}

		@Test
		public void shouldSlice() {
			String[] words = "A B C D E F G H I J".split(" ");
			StringBuffer buf = new StringBuffer();

			for (List<String> each: Each.slice(3, words)) {
				buf.append(each);
			}

			assertEquals("[A, B, C][D, E, F][G, H, I]", buf.toString());
		}

		@Test
		public void shouldNotSliceWhenTooShort() {
			String[] words = "A B".split(" ");
			StringBuffer buf = new StringBuffer();

			for (List<String> each: Each.slice(3, words)) {
				buf.append(each);
			}

			assertEquals("", buf.toString());
		}

	}

}
