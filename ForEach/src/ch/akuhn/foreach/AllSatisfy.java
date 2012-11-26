package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AllSatisfy<Each> extends For<Each> {

	public Each element;
	public boolean yield;

	@Override
	protected void afterEach() {
		if (yield) ;
		else abort();
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
		public void shouldAbortWhenNotMatchingAll() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");
			int count = 0;

			for (AllSatisfy<String> each: Query.with(new AllSatisfy<String>(), words)) {
				each.yield = each.element.equals("The");
				count++;
			}

			assertEquals(false, Query.result());
			assertEquals(2, count);
		}

	}

}
