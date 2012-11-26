package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class Select<Each> extends For<Each> {

	public Each value;
	public boolean yield;

	private Collection<Each> result;

	@Override
	protected void afterEach() {
		if (yield) result.add(value);
	}

	@Override
	protected void beforeEach(Each each) {
		value = each;
		yield = false;
	}

	@Override
	protected void beforeLoop() {
		result = new ArrayList<Each>();
	}

	@Override
	protected Object afterLoop() {
		return result;
	}

	public static class Examples {

		@Test
		public void shouldIncludeShortWords() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");
			for (Select<String> each: Query.with(new Select<String>(), words)) {
				each.yield = each.value.length() < 4;
			}
			assertEquals("[The, fox, the, dog]", Query.result().toString());
		}

	}

}
