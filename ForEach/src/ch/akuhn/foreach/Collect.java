package ch.akuhn.foreach;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Evaluates an expression for each element of a collection, returning a new
 * collection containing the values yielded by the expression. This class is to
 * be used in a for-each loop as follows:
 * 
 * <pre>
 * for (Collect&lt;String&gt; each: ForEach.collect(words)) {
 * 	each.yield = each.value.length();
 * }
 * List&lt;Integer&gt; result = ForEach.result();
 * </pre>
 * <p>
 * The body of the loop should implement an expression. The current element is
 * provided in {@link Collect#value}. The result of the expression must be
 * assigned to {@link Collect#yield}. The body of the loop is evaluated for each
 * element of the collection. The entire loop results in a new collection
 * containing the values yielded by the expression. Upon termination of the loop
 * the new collection is available at {@link ForEach#result()} (a thread local
 * variable).
 * <p>
 * 
 * @param curr
 *            (in) current element of the collection.
 * @param yield
 *            (out) result of the expression, defaults to <tt>null</tt> if not
 *            assigned.
 * 
 * @return a new collection of type {@link List} containing the values assigned
 *         to yield.
 * 
 * @throws NoSuchElementException
 *             if the result is retrieved after exiting the loop with a
 *             <tt>break</tt> statement.
 * 
 */
public class Collect<Each> extends For<Each> {

	public Each value;
	public Object yield;

	private List result;

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
		result = new ArrayList();
	}

	public static class Examples {

		@Test
		public void shouldMapLength() {
			String[] words = "The quick brown fox jumps over the lazy dog".split(" ");

			for (Collect<String> each: ForEach.collect(words)) {
				each.yield = each.value.length();
			}
			assertEquals("[3, 5, 5, 3, 5, 4, 3, 4, 3]", ForEach.result().toString());
		}

	}

}
