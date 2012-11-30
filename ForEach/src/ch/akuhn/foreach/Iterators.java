package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public abstract class Iterators {

	public static final <A, B> Iterable<Pair<A, B>> zip(final Iterable<A> fst, final Iterable<B> snd) {
		return new Iterable<Pair<A, B>>() {
			public Iterator<Pair<A, B>> iterator() {
				return new Iterator<Pair<A, B>>() {
					private final Iterator<A> a = fst.iterator();
					private final Iterator<B> b = snd.iterator();
	
					public boolean hasNext() {
						return a.hasNext() && b.hasNext();
					}
	
					public Pair<A, B> next() {
						if (!hasNext()) throw new NoSuchElementException();
						return Pair.of(a.next(), b.next());
					}
	
					public void remove() {
						a.remove();
						b.remove();
					}
				};
			}
		};
	}

	/**
	 * Iterate over all consecutive pairs of <tt>elements</tt>.
	 * 
	 * @return if <tt>elements</tt> has less than two elements, the returned
	 *         iterable is empty.
	 */
	public static final <E> Iterable<Pair<E, E>> pairs(final Iterable<E> iterable) {
		return new Iterable<Pair<E, E>>() {
			public Iterator<Pair<E, E>> iterator() {
				return new Iterator<Pair<E, E>>() {
					private final Iterator<E> it = iterable.iterator();
					private E prev = it.hasNext() ? it.next() : null;
	
					public boolean hasNext() {
						return it.hasNext();
					}
	
					public Pair<E, E> next() {
						if (!hasNext()) throw new NoSuchElementException();
						return Pair.of(prev, prev = it.next());
					}
	
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

	public static <T> Iterable<List<T>> cons(int length, T[] all) {
		return Iterators.cons(length, Arrays.asList(all));
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
		return Iterators.slice(length, Arrays.asList(all));
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

			for (List<String> each: Iterators.cons(3, words)) {
				buf.append(each);
			}

			assertEquals("[A, B, C][B, C, D][C, D, E]", buf.toString());
		}

		@Test
		public void shouldNotIterateWhenTooShort() {
			String[] words = "A B".split(" ");
			StringBuffer buf = new StringBuffer();

			for (List<String> each: Iterators.cons(3, words)) {
				buf.append(each);
			}

			assertEquals("", buf.toString());
		}

		@Test
		public void shouldSlice() {
			String[] words = "A B C D E F G H I J".split(" ");
			StringBuffer buf = new StringBuffer();

			for (List<String> each: Iterators.slice(3, words)) {
				buf.append(each);
			}

			assertEquals("[A, B, C][D, E, F][G, H, I]", buf.toString());
		}

		@Test
		public void shouldNotSliceWhenTooShort() {
			String[] words = "A B".split(" ");
			StringBuffer buf = new StringBuffer();

			for (List<String> each: Iterators.slice(3, words)) {
				buf.append(each);
			}

			assertEquals("", buf.toString());
		}

	}

}
