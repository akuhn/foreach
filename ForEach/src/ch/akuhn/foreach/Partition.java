package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Partition<Each> extends For<Each> {

	public Each value;
	public boolean yield;

	private List<Each> select;
	private List<Each> reject;

	@Override
	protected void beforeLoop() {
		select = new ArrayList();
		reject = new ArrayList();
	}

	@Override
	protected void beforeEach(Each each) {
		this.value = each;
		this.yield = false;
	}

	@Override
	protected void afterEach() {
		(yield ? select : reject).add(value);
	}

	@Override
	protected Object afterLoop() {
		return Pair.of(select, reject);
	}

	public static <E> List<E> selected() {
		return ((Pair<List<E>,?>) ForEach.result()).fst;
	}

	public static <E> List<E> rejected() {
		return ((Pair<?,List<E>>) ForEach.result()).snd;
	}

	public static class Examples {

		@Test
		public void shouldPartitionByLength() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");
			for (Partition<String> each: ForEach.partition(words)) {
				each.yield = each.value.length() < 4;
			}
			assertEquals("[The, fox, the, dog]", Partition.selected().toString());
			assertEquals("[quick, brown, jumps, over, lazy]", Partition.rejected().toString());
		}

	}

}
