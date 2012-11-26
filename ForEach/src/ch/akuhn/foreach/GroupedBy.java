package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class GroupedBy<Each> extends For<Each> {

	public Each value;
	public Object yield;

	private Map<Object, List<Each>> groups;

	@Override
	protected void afterEach() {
		if (yield == null) throw new NullPointerException();
		List<Each> group = groups.get(yield);
		if (group == null) {
			groups.put(yield, group = new ArrayList<Each>());
		}
		group.add(value);
	}

	@Override
	protected void beforeEach(Each each) {
		value = each;
		yield = null;
	}

	@Override
	protected void beforeLoop() {
		groups = new HashMap<Object, List<Each>>();
	}

	@Override
	protected Object afterLoop() {
		return groups;
	}

	public static class Examples {

		@Test
		public void shouldGroupByLength() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

			for (GroupedBy<String> each: ForEach.groupedBy(words)) {
				each.yield = each.value.length();
			}

			Map groups = ForEach.result();
			assertEquals("[The, fox, the, dog]", groups.get(3).toString());
			assertEquals("[over, lazy]", groups.get(4).toString());
			assertEquals("[quick, brown, jumps]", groups.get(5).toString());
			assertEquals(3, groups.size());
		}

	}

}
