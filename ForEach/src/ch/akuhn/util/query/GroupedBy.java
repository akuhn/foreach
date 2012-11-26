package ch.akuhn.util.query;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class GroupedBy<Each, Key> extends For<Each> {

	public Each element;
	public Key yield;

	private Map<Key, List<Each>> groups;

	@Override
	protected void afterEach() {
		if (yield == null) throw new NullPointerException();
		List<Each> group = groups.get(yield);
		if (group == null) {
			groups.put(yield, group = new ArrayList<Each>());
		}
		group.add(element);
	}

	@Override
	protected void beforeEach(Each each) {
		element = each;
		yield = null;
	}

	@Override
	protected void beforeLoop() {
		groups = new HashMap<Key, List<Each>>();
	}

	@Override
	protected Object afterLoop() {
		return groups;
	}

	public static class Examples {

		@Test
		public void shouldGroupByLength() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

			for (GroupedBy<String, Integer> each: Query.with(new GroupedBy<String, Integer>(), Arrays.asList(words))) {
				each.yield = each.element.length();
			}

			Map groups = Query.result();
			assertEquals("[The, fox, the, dog]", groups.get(3).toString());
			assertEquals("[over, lazy]", groups.get(4).toString());
			assertEquals("[quick, brown, jumps]", groups.get(5).toString());
			assertEquals(3, groups.size());
		}

	}

}
