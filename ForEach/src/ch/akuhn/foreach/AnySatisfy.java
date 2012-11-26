package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AnySatisfy<Each> extends For<Each> {

	public Each element;
	public boolean yield;

	@Override
	protected void afterEach() {
		if (yield) abort();
	}

	@Override
	protected void beforeEach(Each each) {
		element = each;
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

			for (AnySatisfy<String> each: Query.with(new AnySatisfy<String>(), words)) {
				each.yield = each.element.equals("over");
				count++;
			}

			assertEquals(6, count);
			assertEquals(true, Query.result());
		}

	}

}
