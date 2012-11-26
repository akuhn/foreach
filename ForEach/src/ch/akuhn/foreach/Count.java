package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Count<Each> extends For<Each> {

	public Each value;
	public boolean yield;

	private int count;

	@Override
	protected void afterEach() {
		if (yield) count++;
	}

	@Override
	protected Object afterLoop() {
		return count;
	}

	@Override
	protected void beforeEach(Each each) {
		value = each;
		yield = false;
	}

	@Override
	protected void beforeLoop() {
		count = 0;
	}

	public static class Examples {

		@Test
		public void shouldIncludeShortWords() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

			for (Count<String> each: Query.with(new Count<String>(), words)) {
				each.yield = each.value.length() == 3;
			}

			assertEquals(4, Query.result());
		}

	}

}
