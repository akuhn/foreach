package ch.akuhn.foreach;

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
 * for (Collect2&lt;E,T&gt; each: ForEach.collect(T.class, elements)) {
 *     each.yield = // apply something to each.get
 * }
 * Collection&lt;T&gt; result = ForEach.result();
 * </pre>
 * <p>
 * The body of the loop should implement an expression. The current element is
 * provided in {@link Collect#value}. The result of the expression must be
 * stored to {@link Collect#yield}. The body of the loop is evaluated for each
 * element of the collection. The entire loop results in a new collection
 * containing the values yielded by the expression. Upon termination of the loop
 * the new collection is assigned to {@link ForEach#result()} (a thread local
 * variable).
 * <p>
 * 
 * @param value
 *            (in) current element of the collection.
 * @param yield
 *            (out) result of the expression. Defaults to <tt>null</tt> if not
 *            assigned.
 *            <p>
 * @author akuhn
 * 
 */
public class Collect<Each, Result> extends For<Each> {

	public Each value;
	public Result yield;

	private Collection<Result> result;

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
		value = each;
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

			for (Collect<String, Integer> each: Query.with(new Collect<String, Integer>(), Arrays.asList(words))) {
				each.yield = each.value.length();
			}
			assertEquals("[3, 5, 5, 3, 5, 4, 3, 4, 3]", Query.result().toString());
		}

	}

}
