package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Inject<Each,Yield> extends For<Each> {

	public Each value;
	public Yield yield;

	public Inject(Yield initial) {
		yield = initial;
	}

	@Override
	protected void afterEach() {
	}

	@Override
	protected Object afterLoop() {
		return yield;
	}

	@Override
	protected void beforeEach(Each each) {
		value = each;
	}

	@Override
	protected void beforeLoop() {
	}

	public static class Examples {

		@Test
		public void shouldSumSquaredLength() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

			for (Inject<String,Integer> each: ForEach.inject(0, words)) {
				each.yield += each.value.length();
			}
			assertEquals(35, ForEach.result());
		}

	}

}