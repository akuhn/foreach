package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class All<Each> extends For<Each> {

	public Each value;
	public boolean yield;

	@Override
	protected void afterEach() {
		if (yield) ;
		else abort();
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
		public void shouldAbortWhenNotMatchingAll() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");
			int count = 0;

			for (All<String> each: ForEach.all(words)) {
				each.yield = each.value.equals("The");
				count++;
			}

			assertEquals(false, ForEach.result());
			assertEquals(2, count);
		}

	}

}
