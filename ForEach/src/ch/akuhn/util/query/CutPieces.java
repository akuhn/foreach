package ch.akuhn.util.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Breaks the collection into pieces. Evaluates the loop for successive pairs of
 * the collection's elements, breaking the collection into pieces between
 * elements where the loop yields `true`. Returns a list of those pieces. To be
 * used in a foreach loop as follows:
 * 
 * <pre>
 * for (CutPieces&lt;T&gt; each: cutPieces(elements)) {
 *     each.cutIf = &amp;hellip each.value &amp;hellip each.next &amp;hellip ;
 * }
 * List&lt;Collection&lt;T&gt;&gt; pieces = $result;
 * </pre>
 * <p>
 * The body of the loop should implement a predicate. The current pair of
 * elements is provided in `each.value` and `each.next`. The result of the
 * predicate must be stored to `each.yield`. The body of the loop is evaluated
 * for consecutive pairs of the collection. The loop cuts the collection into
 * pieces and returns those.
 * <ul>
 * <li>If a pair yields `true`, the collection is cut between those elements.
 * <li>If a pair yields `false` or nothing, no cut happens.
 * </ul>
 * <p>
 * <i>Example:</i> splitting a series of events into clusters that are no more
 * than 2 minutes apart.
 * 
 * <pre>
 * SortedList&lt;Events&gt; events = &amp;hellip ;
 * for (CutPieces&lt;Event&gt; $: cutPieces(events)) {
 *     $.cutIf = ($.next.time() - $.value.time()) &gt; 120000;
 * }
 * List&lt;List&lt;Event&gt;&gt; clusters = $result;
 * </pre>
 * <p>
 * 
 * @param value
 *            (in) first element of the current pair. No effect if assigned.
 * @param next
 *            (in/out) second element of the current pair. If assigned, the new
 *            value is used in the resulting pieces.
 * @param cutIf
 *            (out) result of the predicate. If `true` the collection is cut
 *            between the elements of the current pair. Defaults to `false` if
 *            not assigned.
 *            <P>
 * @author Adrian Kuhn
 * 
 */
public class CutPieces<Each> extends ForPair<Each, CutPieces<Each>> {

	private Collection<Each> current;
	public Each next;
	public Each prev;

	private Collection<Collection<Each>> result;
	public boolean yield;

	@Override
	protected void afterEach() {
		if (yield) {
			current = new ArrayList<Each>();
			result.add(current);
		}
		current.add(next);
	}

	@Override
	protected Object afterLoop() {
		return result;
	}

	@Override
	protected void beforeEach(Each previous, Each element) {
		prev = previous;
		next = element;
		yield = false;
	}

	@Override
	protected void beforeLoop(Each first) {
		result = new LinkedList<Collection<Each>>();
		current = new ArrayList<Each>();
		result.add(current);
		current.add(first);
	}

}
