package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Detect<Each> extends For<Each> {

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
		return value;
	}

	public static class Examples {

		@Test
		public void shouldFindMatchingElement() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");
			int count = 0;

			for (Detect<String> each: Query.with(new Detect<String>(), words)) {
				each.yield = each.value.length() == 4;
				count++;
			}

			assertEquals(6, count);
			assertEquals("over", Query.result());
		}

	}

}
