package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

public abstract class For<Each> {

	protected abstract void beforeLoop();

	protected abstract void beforeEach(Each each);

	protected abstract void afterEach();

	protected abstract Object afterLoop();

	protected void abort() {
		throw new Abortion();
	}

}

@SuppressWarnings("serial")
class Abortion extends RuntimeException {

}

@SuppressWarnings("hiding")
class Iter<Each, ForEach extends For<Each>> implements Iterator<ForEach> {

	private ForEach each;
	private Iterator<Each> iterator;

	public static final int BEFORE_LOOP = 0;
	public static final int BEFORE_EACH = 1;
	public static final int AFTER_EACH = 2;
	public static final int AFTER_LOOP = 3;

	private int state;

	public Iter(ForEach each, Iterator<Each> iterator) {
		this.each = each;
		this.iterator = iterator;
		state = BEFORE_LOOP;
	}

	@Override
	public boolean hasNext() {
		switch (state) {
		case BEFORE_LOOP:
			each.beforeLoop();
			state = BEFORE_EACH;
			break;
		case AFTER_EACH:
			try {
				each.afterEach();
				state = BEFORE_EACH;
			} catch (Abortion abort) {
				state = AFTER_LOOP;
				Object result = each.afterLoop();
				Query.offer(result);
				return false;
			}
			break;
		case AFTER_LOOP:
			return false;
		}
		boolean hasNext = iterator.hasNext();
		if (!hasNext) {
			state = AFTER_LOOP;
			Object result = each.afterLoop();
			Query.offer(result);
		}
		return hasNext;
	}

	@Override
	public ForEach next() {
		switch (state) {
		case BEFORE_EACH:
			Each element = iterator.next();
			each.beforeEach(element);
			state = AFTER_EACH;
			return each;
		case AFTER_LOOP:
			throw new NoSuchElementException();
		default:
			throw new IllegalStateException();
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	public static class Examples {

		@Test
		public void shouldGoThroughAllStates() {

			class Example extends For<Integer> {

				public StringBuilder buf = new StringBuilder();

				@Override
				public void afterEach() {
					buf.append(')');
				}

				@Override
				public void beforeEach(Integer each) {
					buf.append('(');
				}

				@Override
				public void beforeLoop() {
					buf.append('^');
				}

				@Override
				public Object afterLoop() {
					buf.append('$');
					return buf.toString();
				}

			};

			List<Integer> list = Arrays.asList(1, 2, 3);
			for (Example each: Query.with(new Example(), list)) {
				each.buf.append('.');
			};

			assertEquals("^(.)(.)(.)$", Query.result());

		}
	}

}
