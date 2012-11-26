package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Any<Each> extends For<Each> {

	public Each value;
	public boolean yield;

	@Override
	protected void afterEach() {
		if (yield) abort();
	}

	@Override
	protected void beforeEach(Each each) {
		value = each;
		yield = false;
	}

	@Override
	protected void beforeLoop() {
	}

	@Override
	protected Object afterLoop() {
		return yield;
	}

	public static class Examples {

		@Test
		public void shouldAbortOnFirstMatch() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");
			int count = 0;

			for (Any<String> each: ForEach.any(words)) {
				each.yield = each.value.equals("over");
				count++;
			}

			assertEquals(6, count);
			assertEquals(true, ForEach.result());
		}

	}

}
