package ch.akuhn.util.query;

import java.util.ArrayList;
import java.util.Collection;

import ch.akuhn.util.query.For.Each;

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
public class Collect2<Each, R> extends For<Each, Collect2<Each, R>> {

	private Collection<R> copy;
	public Each element;
	public R yield;

	@Override
	protected void afterEach() {
		copy.add(yield);
	}

	@Override
	protected Object afterLoop() {
		return copy;
	}

	@Override
	protected void beforeEach(Each each) {
		element = each;
		yield = null;
	}

	@Override
	protected void beforeLoop() {
		copy = new ArrayList<R>();
	}

	public static <T, R> Collect2<T, R> from(Iterable<? extends T> elements, Class<R> returnType) {
		return new Collect2<T, R>().with(elements);
	}

	public static <T> Collect2<T, T> from(Iterable<? extends T> elements) {
		return new Collect2<T, T>().with(elements);
	}

	public Collection<R> result() {
		return copy;
	}

}
