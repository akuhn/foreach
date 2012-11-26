package ch.akuhn.util.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Detect<Each> extends For<Each> {

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
		return element;
	}

	public static class Examples {

		@Test
		public void shouldFindMatchingElement() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");
			int count = 0;

			for (Detect<String> each: Query.with(new Detect<String>(), words)) {
				each.yield = each.element.length() == 4;
				count++;
			}

			assertEquals(6, count);
			assertEquals("over", Query.result());
		}

	}

}
