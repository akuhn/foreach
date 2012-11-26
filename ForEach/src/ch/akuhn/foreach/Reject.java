package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class Reject<Each> extends For<Each> {

	public Each element;

	public boolean yield;

	private Collection<Each> result;

	@Override
	protected void afterEach() {
		if (yield) ;
		else result.add(element);
	}

	@Override
	protected void beforeEach(Each each) {
		element = each;
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
		public void shouldExcludeShortWords() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

			for (Reject<String> each: Query.with(new Reject<String>(), words)) {
				each.yield = each.element.length() < 4;
			}

			assertEquals("[quick, brown, jumps, over, lazy]", Query.result().toString());
		}

	}

}
