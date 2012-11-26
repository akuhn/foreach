package ch.akuhn.util.query;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class Inject<Each, Result> extends For<Each> {

	public Each element;
	public Result yield;

	public Inject(Result initial) {
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
		element = each;
	}

	@Override
	protected void beforeLoop() {
	}

	public static class Examples {

		@Test
		public void shouldSumSquaredLength() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

			for (Inject<String, Integer> each: Query.with(new Inject<String, Integer>(0), Arrays.asList(words))) {
				each.yield += each.element.length();
			}
			assertEquals(35, Query.result());
		}

	}

}