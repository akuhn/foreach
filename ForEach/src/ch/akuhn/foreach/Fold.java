package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Fold<Each> extends For<Pair<Each,Each>> {

	public Each value;
	public Each sum;

	private boolean first;

	@Override
	protected void beforeLoop() {
		first = true;
	}

	@Override
	protected void beforeEach(Pair<Each,Each> each) {
		if (first) {
			this.sum = each.fst;
			first = false;
		}
		this.value = each.snd;
	}

	@Override
	protected void afterEach() {
	}

	@Override
	protected Object afterLoop() {
		return sum;
	}

	public static class Examples {

		@Test
		public void shouldFoldNumbers() {
			Integer[] numbers = { 3, 5, 5, 3, 5, 4, 3, 4, 3 };

			for (Fold<Integer> each: ForEach.fold(numbers)) {
				each.sum = each.sum + each.value;
			}

			assertEquals(35, ForEach.result());
		}

	}

}
