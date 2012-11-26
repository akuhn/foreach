package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Sum<Each> extends For<Each> {

	public Each value;
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
		this.value = each;
	}

	@Override
	protected void beforeLoop() {
		sum = 0;
	}

	public static class Examples {

		@Test
		public void shouldSumSquaredLength() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

			for (Sum<String> each: ForEach.sum(words)) {
				each.sum += each.value.length();
			}
			assertEquals(35, ForEach.result());
		}

	}

}