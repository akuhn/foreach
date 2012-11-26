package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IndexOf<Each> extends For<Each> {

	public Each element;
	public boolean yield;
	private int index;

	@Override
	protected void afterEach() {
		if (yield) abort();
		else index++;
	}

	@Override
	protected void beforeEach(Each each) {
		element = each;
	}

	@Override
	protected void beforeLoop() {
		yield = false;
	}

	@Override
	protected Object afterLoop() {
		return yield ? index : -1;
	}

	public static class Examples {

		@Test
		public void shouldFindMatchingElement() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");
			int count = 0;

			for (IndexOf<String> each: Query.with(new IndexOf<String>(), words)) {
				each.yield = each.element.length() == 4;
				count++;
			}

			assertEquals(6, count);
			assertEquals(5, Query.result());
		}

	}

}
