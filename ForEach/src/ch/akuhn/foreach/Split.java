package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Breaks the collection into pieces. Evaluates the loop for successive pairs of
 * the collection's elements, breaking the collection into pieces between
 * elements where the loop yields <tt>true</tt>. Returns a list of those pieces.
 * To be used in a foreach loop as follows:
 * 
 * <p>
 * The body of the loop should implement a predicate. The current pair of
 * elements is provided in <tt>each.curr</tt> and <tt>each.next</tt>. The result
 * of the predicate must be stored to <tt>each.yield</tt>. The body of the loop
 * is evaluated for consecutive pairs of the collection. The loop cuts the
 * collection into pieces and returns those,
 * <ul>
 * <li>if a pair yields <tt>true</tt> the collection is cut between those
 * elements,
 * <li>if a pair yields <tt>false</tt> or nothing no cut happens.
 * </ul>
 * <p>
 * <i>Example:</i> splitting a series of events into clusters that are no more
 * than 2 minutes apart.
 * 
 * <pre>
 * SortedList&lt;Events&gt; events = ...
 * for (Split&lt;Event&gt; each: ForEach.split(events)) {
 *     each.yield = each.next.time() &gt; each.curr.time() + 120;
 * }
 * List&lt;List&lt;Event&gt;&gt; clusters = ForEach.result();
 * </pre>
 * <p>
 * 
 * @param curr
 *            (in/out) first element of the current pair. If assigned in the
 *            first iteration, the new value is used in the resulting pieces.
 * @param next
 *            (in/out) second element of the current pair. If assigned, the new
 *            value is used in the resulting pieces.
 * @param yield
 *            (out) result of the predicate. If <tt>true</tt> the collection is
 *            split between the elements of the current pair. Defaults to
 *            <tt>false</tt> if not assigned.
 * 
 * @return a value of type <tt>List&lt;List&ltEach&gt;&gt;
 * 
 */
public class Split<Each> extends For<Pair<Each, Each>> {

	public Each curr;
	public Each next;
	public boolean yield;

	private List<List<Each>> parts;
	private List<Each> last;

	@Override
	protected void beforeLoop() {
		parts = new ArrayList();
	}

	@Override
	protected void beforeEach(Pair<Each, Each> each) {
		this.curr = each.fst;
		this.next = each.snd;
		this.yield = false;
	}

	@Override
	protected void afterEach() throws Abortion {
		if (last == null) {
			parts.add(last = new ArrayList());
			last.add(this.curr);
		}
		if (yield == true) parts.add(last = new ArrayList());
		last.add(this.next);
	}

	@Override
	protected Object afterLoop() {
		return parts;
	}

	public static class Examples {

		@Test
		public void shouldSplitIntoRunsOfDecreasingLength() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

			for (Split<String> each: ForEach.split(words)) {
				each.yield = each.curr.length() < each.next.length();
			}

			assertEquals("[[The], [quick, brown, fox], [jumps, over, the], [lazy, dog]]", ForEach.result().toString());
		}

	}

}
