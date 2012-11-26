package ch.akuhn.util.query;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class Sum<Each> extends For<Each> {

	public Each element;

	public int sum;

	@Override
	protected void afterEach() {
	}

	@Override
	protected Object afterLoop() {
		return sum;
	}

	@Override
	protected void beforeEach(Each each) {
		this.element = each;
	}

	@Override
	protected void beforeLoop() {
		sum = 0;
	}

	public static class Examples {

		@Test
		public void shouldSumSquaredLength() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

			for (Sum<String> each: Query.with(new Sum<String>(), Arrays.asList(words))) {
				each.sum += each.element.length();
			}
			assertEquals(35, Query.result());
		}

	}

}