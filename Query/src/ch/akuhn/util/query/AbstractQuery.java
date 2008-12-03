package ch.akuhn.util.query;

import java.util.Collection;
import java.util.Iterator;


/**
 * 
 * @author Adrian Kuhn
 *
 * @param <E>
 */
public abstract class AbstractQuery<E,X extends AbstractFrame<E>> 
	implements Iterable<X>, Iterator<X> {
	
	private static Object $result;
	
	static void offer(Object result) {
		if ($result != null) throw new AssertionError();
		$result = result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T result() {
		Object result = $result;
		$result = null;
		return (T) result;
	}
	
	protected final X each;
	private final Iterator<E> iter;
	protected final Collection<E> source;
	protected State state = State.A;
	
	public AbstractQuery(Collection<E> source) {
		this.source = source;
		this.iter = source.iterator();
		this.each = createEach();
		this.initialize();
		this.state = State.A;
	}
	
	protected abstract X createEach();

	protected abstract void apply();

	@Override
	public boolean hasNext() {
		assert state != State.A;
		if (state == State.D) {
			this.apply();
			state = State.C;
		}
		boolean hasNext = iter.hasNext();
		if (!hasNext && state != State.E) {
			AbstractQuery.offer(this.release());
			state = State.E;
		}
		return hasNext;
	}

	protected abstract void initialize();

	@Override
	public Iterator<X> iterator() {
		assert state == State.A;
		state = State.C;
		return this;
	}
	
	@Override
	public X next() {
		assert state == State.C;
		each.each = iter.next();
		state = State.D;
		return each;
	}

	protected abstract Object release();
		
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}