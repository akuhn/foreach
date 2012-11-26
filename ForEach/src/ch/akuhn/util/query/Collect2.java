package ch.akuhn.util.query;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

/**
 * Evaluates an expression for each element of a collection, returning a new
 * collection containing the values yielded by the expression. This class is to
 * be used in a for-each loop as follows:
 * 
 * <pre>
 * for (Collect&lt;E,T&gt; each: Query.collect(elements, T.class)) {
 *     each.yield = &amp;hellip each.value &amp;hellip;
 * }
 * Collection&lt;T&gt; copy = Query.$result();
 * </pre>
 * <p>
 * The body of the loop should implement an expression. The current element is
 * provided in `each.value`. The result of the expression must be stored to
 * `each.yield`. The body of the loop is evaluated for each element of the
 * collection. The entire loop results in a new collection containing the values
 * yielded by the expression. Upon termination of the loop the new collection is
 * assigned to $result (a thread local variable).
 * <p>
 * 
 * @param value
 *            (in) current element of the collection.
 * @param yield
 *            (out) result of the expression. Defaults to `null` if not
 *            assigned.
 *            <p>
 * @author Adrian Kuhn
 * 
 */
public class Collect2<Each, Result> extends For<Each> {

	private Collection<Result> result;
	public Each element;
	public Result yield;

	@Override
	protected void afterEach() {
		result.add(yield);
	}

	@Override
	protected Object afterLoop() {
		return result;
	}

	@Override
	protected void beforeEach(Each each) {
		element = each;
		yield = null;
	}

	@Override
	protected void beforeLoop() {
		result = new ArrayList<Result>();
	}

	public static class Examples {

		@Test
		public void shouldMapLength() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

			for (Collect2<String, Integer> each: Query.with(new Collect2<String, Integer>(), Arrays.asList(words))) {
				each.yield = each.element.length();
			}
			assertEquals("[3, 5, 5, 3, 5, 4, 3, 4, 3]", Query.result().toString());
		}

	}

}
